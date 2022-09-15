package ru.gorbunova.tictactoe.gameLogic.localServerGame

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import eac.network.Connection
import eac.network.PackageReceiver
import eac.network.PackageSender
import ru.gorbunova.tictactoe.App
import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import ru.gorbunova.tictactoe.gameLogic.base.IEngine
import ru.gorbunova.tictactoe.gameLogic.base.IPlayer
import ru.gorbunova.tictactoe.gameLogic.networkGame.GameEngineNetwork
import ru.gorbunova.tictactoe.gameLogic.networkGame.NetworkPlayer

class FakePlayer : NetworkPlayer(User()) {

    private var connection: Connection? = null
    private val sender = PackageSender()
    private val receiver = PackageReceiver()

    private var listenerCallback: ((GameEngineNetwork.Command?) -> Unit)? = null
    private val connectionListener: (Connection, ByteArray) -> Unit = { _, bytes ->
        val command = GameEngineNetwork.Command(bytes.decodeToString())
        val callback = listenerCallback
        if(callback != null) {
            listenerCallback = null
            callback(command)
        } else {
            when(command.name) {
                GameEngineNetwork.COMMAND_READY -> App.handler.post { ready() }
                GameEngineNetwork.COMMAND_CELL -> {
                    App.handler.post { try {
                        super.executeMove(command.data!!.toInt())
                    } catch (th: Throwable) {
                        th.printStackTrace()
                    }}
                }
                GameEngineNetwork.COMMAND_GAME -> render()
                GameEngineNetwork.COMMAND_EXIT -> destroy()
            }
        }
    }

    override fun executeMove(indexCell: Int) {}

    override fun setEngine(engine: IEngine) {
        super.setEngine(engine)
        engine.addListener { render() }
    }

    override fun isOnline() = connection != null

    private fun render() {
        send(GameEngineNetwork.COMMAND_RENDER, renderToJson(getEngine()))
    }

    private fun playersToJson(player: IPlayer, engine: IEngine): JsonObject {
        val jsonPlayers = JsonObject()

        jsonPlayers.addProperty("userId", player.getId())
        jsonPlayers.addProperty("userLogin", player.getName())

        jsonPlayers.addProperty("action",
            engine.getActionPlayer()?.getActionType() == player.getActionType())
        jsonPlayers.addProperty("actionType", player.getActionType())
        jsonPlayers.addProperty("winCounter", player.getScore())
        jsonPlayers.addProperty("isOnline", player.isOnline())
        jsonPlayers.addProperty("isReady", player.isReady())

        return jsonPlayers
    }

    private fun renderToJson (engine: IEngine): String {

        val player1 = engine.getPlayer1()
        val player2 = engine.getPlayer2()
        val state = engine.getState()

        return JsonObject().apply {

            addProperty("status", state.getStatus())

            add("game", JsonArray().apply {
                for(value in state.getCells())
                    add(value)
            })

            add("players", JsonArray().apply {
                player1?.also { add(playersToJson(it,engine)) }
                player2?.also { add(playersToJson(it,engine)) }
            })

            state.getWinner()?.also { add("winner", playersToJson(it, engine)) }
        }.toString()
    }

    private fun send(command: GameEngineNetwork.Command) {
        sender?.send("$command".also { println(it) })
    }

    private fun send(name: String, data: String? = null) {
        send(GameEngineNetwork.Command(name).apply { this.data = data })
    }

    fun setConnection(connection: Connection) {
        this.connection = connection
        sender.register(connection)
        receiver.register(connection, connectionListener)
        connection.setOnDisconnected<Connection> {
            destroy()
        }

        App.handler.post { getEngine().render() }
    }

    private fun destroy() {

        connection?.also {
            connection = null
            it.shutdown()
        } ?: return

        sender.unregister()
        receiver.unregister()

        App.handler.post { getEngine().render() }
    }
}
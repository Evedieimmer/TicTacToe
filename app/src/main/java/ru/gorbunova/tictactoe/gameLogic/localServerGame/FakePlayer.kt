package ru.gorbunova.tictactoe.gameLogic.localServerGame

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import eac.network.Connection
import eac.network.PackageReceiver
import eac.network.PackageSender
import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import ru.gorbunova.tictactoe.gameLogic.base.IEngine
import ru.gorbunova.tictactoe.gameLogic.base.IPlayer
import ru.gorbunova.tictactoe.gameLogic.networkGame.GameEngineNetwork
import ru.gorbunova.tictactoe.gameLogic.networkGame.ITokenProvider
import ru.gorbunova.tictactoe.gameLogic.networkGame.NetworkPlayer
import ru.gorbunova.tictactoe.gameLogic.networkGame.RemoteState

class FakePlayer(
    private val user: User
): NetworkPlayer(user), ITokenProvider {

    private val jsonSend = JsonObject()
    private val jsonReceive = Gson()

    private val connection: Connection? = null
    private var listenerCallback: ((GameEngineNetwork.Command?) -> Unit)? = null
    private val connectionListener: (Connection, ByteArray) -> Unit = { _, bytes ->
        val command = GameEngineNetwork.Command(bytes.decodeToString())
         val callback = listenerCallback
        if(callback != null) {
            listenerCallback = null
            callback(command)
        } else {
            when(command.name) {
                GameEngineNetwork.COMMAND_GAME, GameEngineNetwork.COMMAND_READY, GameEngineNetwork.COMMAND_CELL -> {
                    send(GameEngineNetwork.COMMAND_RENDER, "$jsonSend")

                }
                GameEngineNetwork.COMMAND_EXIT -> {
                    receiver.unregister()
                    sender.unregister()
                 }
            }
        }
    }
    private val sender = PackageSender(connection)
    private val receiver = PackageReceiver(connection, connectionListener)

    val listener: (IEngine) -> Unit = { engine ->
        renderToJson(engine)
    }

    override fun setEngine(engine: IEngine) {
        super.setEngine(engine)
        engine.addListener(listener)
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

        jsonSend.addProperty("status", state.getStatus())

        jsonSend.add("game", JsonArray().apply {
            for(value in state.getCells())
                add(value)
        })

        jsonSend.add("players", JsonArray().apply {
            player1?.also { add(playersToJson(it,engine)) }
            player2?.also { add(playersToJson(it,engine)) }
        })

        state.getWinner()?.also { jsonSend.add("winner", playersToJson(it, engine)) }

        return "$jsonSend"
    }

    private fun convertRenderToReceive(command: GameEngineNetwork.Command) {
        jsonReceive.fromJson(
            command.data
                ?: throw IllegalStateException("Error"), RemoteState::class.java
        )
    }

    private fun send(command: GameEngineNetwork.Command) {
        sender.send("$command".also { println("$it") })
    }

    private fun send(name: String, data: String? = null) {
        send(GameEngineNetwork.Command(name).apply { this.data = data })
    }

    override fun provideToken(): String = user.token?.access ?: ""

    override fun onAuthError(engine: IEngine, e: Exception) {}
}
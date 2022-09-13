package ru.gorbunova.tictactoe.gameLogic.localServerGame

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
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



    val listener: (IEngine) -> Unit = { engine ->
        val state = engine.getState()
        val player1 = engine.getPlayer1()
        val player2 = engine.getPlayer2()
        engine.getActionPlayer()

        renderToJson(engine, player1, player2)
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

    private fun renderToJson (engine: IEngine, player1: IPlayer?, player2: IPlayer?): String {

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

    private fun onCommand(command: GameEngineNetwork.Command) {
        when (command.name) {
            GameEngineNetwork.COMMAND_GAME, GameEngineNetwork.COMMAND_READY -> {

            }
            GameEngineNetwork.COMMAND_CELL -> {
                //выполнить ход и отправить рендер
            }
            GameEngineNetwork.COMMAND_EXIT -> {
                //завершить игру
            }
        }
    }

    private fun sendToNetworkPlayer() {

    }

    private fun receiveFromNetworkPlayer() {

    }

    override fun provideToken(): String = ""

    override fun onAuthError(engine: IEngine, e: Exception) {}
}
package ru.gorbunova.tictactoe.gameLogic.localServerGame

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import ru.gorbunova.tictactoe.gameLogic.base.IEngine
import ru.gorbunova.tictactoe.gameLogic.base.IGameState
import ru.gorbunova.tictactoe.gameLogic.base.IPlayer
import ru.gorbunova.tictactoe.gameLogic.networkGame.GameEngineNetwork
import ru.gorbunova.tictactoe.gameLogic.networkGame.NetworkPlayer

class FakePlayer(
    private val user: User
): NetworkPlayer(user) {

    private val jsonSend = JsonObject()
    private val jsonReceive = Gson()

    val listener: (IEngine) -> Unit = { engine ->
        val state = engine.getState()
        val player1 = engine.getPlayer1()
        val player2 = engine.getPlayer2()

        renderToJson(state, player1, player2)
    }

    override fun setEngine(engine: IEngine) {
        super.setEngine(engine)
        engine.addListener(listener)
    }

    private fun playersToJson(player: IPlayer): JsonObject {
        val jsonPlayers = JsonObject()
        jsonPlayers.addProperty("userId", player.getId())
        jsonPlayers.addProperty("userLogin", player.getName())

//        jsonPlayers.addProperty("action")
        jsonPlayers.addProperty("actionType", player.getActionType())
        jsonPlayers.addProperty("winCounter", player.getScore())
        jsonPlayers.addProperty("isOnline", player.isOnline())
        jsonPlayers.addProperty("isReady", player.isReady())

        return jsonPlayers
    }

    private fun renderToJson (state: IGameState, player1: IPlayer?, player2: IPlayer?): String {

        jsonSend.addProperty("status", state.getStatus())

        val game = JsonArray()
        for(value in state.getCells())
            game.add(value)
        jsonSend.add("game", game)

        val players = JsonArray()
        players.add(player1?.let { playersToJson(it) })
        players.add(player2?.let { playersToJson(it) })

        jsonSend.add("players", players)

        jsonSend.add("winner", state.getWinner()?.let { playersToJson(it) })

        return "RENDER:${jsonSend}"
    }

    private fun convertRenderToReceive(command: GameEngineNetwork.Command) {
//        jsonReceive.fromJson(command.data) ?: throw IllegalStateException("Не пришел рендер"), RemoteState::class.java
    }

    private fun onCommand() {

    }

    private fun sendToNetworkPlayer() {

    }

    private fun receiveFromNetworkPlayer() {

    }
}
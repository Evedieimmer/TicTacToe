package ru.gorbunova.tictactoe.gameLogic

import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import ru.gorbunova.tictactoe.gameLogic.networkGame.NetworkPlayer
import kotlin.random.Random


class BotPlayer(
    private val user: User,
    private val doEndGame: Boolean
) : NetworkPlayer(user) {

    override fun setEngine(engine: IEngine) {
        super.setEngine(engine)
        engine.addListener { engine ->

            // Начало
            val state = engine.getState()
            if(state.getStatus() == IGameState.STATE_WAITING_PLAYERS_READY)
                onStartGame()

            //в процессе
            else if(state.getStatus() == IGameState.STATE_GAME_PROCESSING)
                onProcessing()

            //завершение
            else
                onEndGame()
        }
    }

    private fun onStartGame() {

        ready()
    }

    private fun onProcessing() {

    }

    private fun onEndGame() {
        if(doEndGame) ServiceGame.endGame()
    }

    private fun randomBotCell(state: IGameState) : Int {
        val gameCells = state.getCells()
        val randomIndex = Random.nextInt(gameCells.size)
        return if (gameCells[randomIndex] == 1) randomIndex
        else randomBotCell(state)
    }


}

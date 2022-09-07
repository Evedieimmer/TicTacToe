package ru.gorbunova.tictactoe.gameLogic

import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import ru.gorbunova.tictactoe.gameLogic.networkGame.NetworkPlayer
import java.lang.IllegalStateException
import kotlin.random.Random


class BotPlayer(
    private val user: User,
    private val doEndGame: Boolean = false
) : NetworkPlayer(user) {

    override fun getName(): String = "Bot"

    override fun setEngine(engine: IEngine) {
        super.setEngine(engine)
        engine.addListener { engine ->

            getSelf(engine)?.also {
                setReady(it.isReady())
            }

            // Начало
            val state = engine.getState()
            if (state.getStatus() == IGameState.STATE_WAITING_PLAYERS_READY)
                onStartGame(engine)

            //в процессе
            else if (state.getStatus() == IGameState.STATE_GAME_PROCESSING)
                onProcessing(engine)

            //завершение
            else
                onEndGame()
        }
    }

    //Проверить готовность игрока, если готов - то и бот готов
    private fun onStartGame(engine: IEngine) {
        getSelf(engine)?.also {
            if (!it.isReady())
                ready()
        }
    }

    //я - текущий игрок? Если текущий, то мой хой
    private fun onProcessing(engine: IEngine) {
        val state = engine.getState()
        if (engine.getActionPlayer() == this && !engine.isGameOver()) {
            executeMove(randomBotCell(state))
        }
    }

    private fun onEndGame() {
        if (doEndGame) ServiceGame.endGame()
    }

    private fun randomBotCell(state: IGameState): Int {
        val cells = state.getCells()
        while (true) {
            val index = Random.nextInt(9)
            if (cells[index] == IGameState.GAME_CELL_VALUE_NONE)
                return index
        }
    }

    private fun getSelf(engine: IEngine): BotPlayer? {

        engine.getPlayer1()?.also {
            if (it == this)
                return it as BotPlayer
        }

        engine.getPlayer2()?.also {
            if (it == this)
                return it as BotPlayer
        }

        return null
//        throw IllegalStateException()
    }
}

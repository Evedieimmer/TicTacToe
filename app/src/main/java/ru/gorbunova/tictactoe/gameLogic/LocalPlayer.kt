package ru.gorbunova.tictactoe.gameLogic

import ru.gorbunova.tictactoe.gameLogic.IGameState.Companion.GAME_CELL_VALUE_CROSS
import ru.gorbunova.tictactoe.gameLogic.IGameState.Companion.GAME_CELL_VALUE_NONE
import ru.gorbunova.tictactoe.gameLogic.IGameState.Companion.GAME_CELL_VALUE_ZERO


class LocalPlayer() : IPlayer {

    private var actionType = GAME_CELL_VALUE_NONE
    private var gameEngine: IEngine? = null
    private var score = 0

    override fun getActionType(): Int {
        return actionType
    }

    override fun setActionType(value: Int) {
        actionType = value
    }

    override fun setEngine(engine: IEngine) {
        gameEngine = engine
    }

    override fun onWin() {
        score++
    }


    override fun ready() {
        getEngine().ready(this)
    }

    override fun executeMove(indexCell: Int) {
        getEngine().executeMove(this, indexCell)
    }

    override fun getScore() = score

    private fun getEngine() = gameEngine ?: throw IllegalStateException("Игрок не добавлен в игру")
}
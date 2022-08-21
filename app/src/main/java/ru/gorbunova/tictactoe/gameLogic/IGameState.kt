package ru.gorbunova.tictactoe.gameLogic

interface IGameState {
    companion object {
        const val GAME_CELL_VALUE_NONE = -1
        const val GAME_CELL_VALUE_ZERO = 0
        const val GAME_CELL_VALUE_CROSS = 1
    }

    fun getCells(): IntArray
    fun getWinner(): String?
}
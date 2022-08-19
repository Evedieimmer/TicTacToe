package ru.gorbunova.tictactoe.gameLogic

interface IPlayer {

    companion object {
        const val GAME_CELL_VALUE_NONE = -1
        const val GAME_CELL_VALUE_ZERO = 0
        const val GAME_CELL_VALUE_CROSS = 1
    }

    val namePlayer: String
    val score: Int?

    fun startPlay()
    fun ready()
    fun exitGame()
    fun render()
    fun getActionType(): Int
}
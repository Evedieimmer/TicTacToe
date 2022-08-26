package ru.gorbunova.tictactoe.gameLogic

interface IGameState {
    companion object {
        const val STATE_WAITING_PLAYERS_READY = 0
        const val STATE_GAME_PROCESSING = 1
        const val STATE_GAME_END = 2

        const val GAME_CELL_VALUE_NONE = -1
        const val GAME_CELL_VALUE_ZERO = 0
        const val GAME_CELL_VALUE_CROSS = 1
    }

    fun getCells(): IntArray
    fun getWinner(): IPlayer?
    fun setStatus(value: Int)
    fun getStatus(): Int
    fun restart()
    fun isGameOver(): Boolean

    //исключения при отсутствии подключения
    //вид игры онлайн/офлайн
    //движок через API ходит на сервер
}
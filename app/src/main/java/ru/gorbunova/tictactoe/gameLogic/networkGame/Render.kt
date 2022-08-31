package ru.gorbunova.tictactoe.gameLogic.networkGame

import ru.gorbunova.tictactoe.gameLogic.IGameState
import ru.gorbunova.tictactoe.gameLogic.IPlayer

class RemoteState (
    private var status: Int,
    var game: IntArray,
    var players: List<RemotePlayer>,
    var winner: RemotePlayer?
) : IGameState {
    companion object {
        private const val STATE_GAME_END = 3
    }
    override fun getCells(): IntArray = game

    override fun getWinner(): IPlayer? = winner

    override fun setStatus(value: Int) {}

    override fun getStatus(): Int = status

    override fun restart() {}

    override fun isGameOver(): Boolean = status == STATE_GAME_END
}

package ru.gorbunova.tictactoe.gameLogic.networkGame

import ru.gorbunova.tictactoe.gameLogic.base.IGameState
import ru.gorbunova.tictactoe.gameLogic.base.IPlayer

class RemoteState (
    private var status: Int,
    var game: IntArray,
    var players: List<RemotePlayer>,
    var winner: RemotePlayer?
) : IGameState {

    override fun getCells(): IntArray = game

    override fun getWinner(): IPlayer? = winner

    override fun setStatus(value: Int) {}

    override fun getStatus(): Int = status

    override fun restart() {}

    override fun isGameOver(): Boolean = status == IGameState.STATE_GAME_END
}

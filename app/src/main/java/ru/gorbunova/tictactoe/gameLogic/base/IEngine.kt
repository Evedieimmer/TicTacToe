package ru.gorbunova.tictactoe.gameLogic.base

interface IEngine {

    fun initGame(call: (Throwable?) -> Unit)
    fun addPlayer(player: IPlayer)
    fun ready(player: IPlayer)

    fun addListener(l: (IEngine) -> Unit)
    fun removeListener(l: (IEngine) -> Unit)

    fun executeMove(player: IPlayer, indexCell: Int)
    fun getState(): IGameState
    fun getPlayer1(): IPlayer?
    fun getPlayer2(): IPlayer?
    fun getActionPlayer(): IPlayer?
    fun getLocalPlayer(): IPlayer?
    fun isGameOver(): Boolean

    fun endGame()
    fun restart()

    fun render()
}
package ru.gorbunova.tictactoe.gameLogic

interface IEngine {
    fun addListener(l: (IEngine) -> Unit)
    fun removeListener(l: (IEngine) -> Unit)
    fun initGame()
    fun addPlayer(player: IPlayer)
    fun ready(player: IPlayer)
    fun executeMove(player: IPlayer, indexCell: Int)
    fun getState(): IGameState
    fun getPlayer1(): IPlayer
    fun getPlayer2(): IPlayer?
    fun getCurrentPlayer(): IPlayer?
}
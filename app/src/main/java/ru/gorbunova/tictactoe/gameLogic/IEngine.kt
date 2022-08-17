package ru.gorbunova.tictactoe.gameLogic

interface IEngine {
    val stateGame: GameState
    val player: IPlayer

    fun initGame()
    fun executeMove()
    fun isWinner()
    fun turn()
}
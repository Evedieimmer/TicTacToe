package ru.gorbunova.tictactoe.gameLogic

interface IPlayer {
    val namePlayer: String
    val score: Int

    fun startPlay()
    fun ready()
    fun exitGame()
    fun render()
    fun getActionType(): Int
}
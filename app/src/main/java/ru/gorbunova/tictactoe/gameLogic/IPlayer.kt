package ru.gorbunova.tictactoe.gameLogic

interface IPlayer {

    val namePlayer: String
    var score: Int?
    var action: Boolean

    fun ready()
    fun getActionType(): Int
    fun initPlayer(isFirst: Boolean): IPlayer
}
package ru.gorbunova.tictactoe.gameLogic

interface IPlayer {

    //системные фун-ции для инициализации
    fun getActionType(): Int
    fun setActionType(value: Int)
    fun setEngine(engine: IEngine)
    fun onWin()

    //реализация игры
    fun ready()
    fun executeMove(indexCell: Int)
    fun getScore(): Int
    fun getName(): String

}
package ru.gorbunova.tictactoe.gameLogic.base

import ru.gorbunova.tictactoe.gameLogic.base.IEngine

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

    fun getId() : Int
    fun getName(): String
    fun isOnline() : Boolean
    fun isReady(): Boolean
}
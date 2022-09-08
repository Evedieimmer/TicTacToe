package ru.gorbunova.tictactoe.gameLogic.networkGame

import ru.gorbunova.tictactoe.gameLogic.base.IEngine

interface ITokenProvider {
    fun provideToken(): String
    fun onAuthError(engine: IEngine, e: Exception)
}
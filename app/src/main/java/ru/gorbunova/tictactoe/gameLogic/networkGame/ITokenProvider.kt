package ru.gorbunova.tictactoe.gameLogic.networkGame

interface ITokenProvider {
    fun provideToken(): String
    fun onError(e: Exception)
}
package ru.gorbunova.tictactoe.presentation.main

import androidx.fragment.app.Fragment

interface INavigateRouterMain {
    fun showLocalGame()
    fun showNetworkGame()
    fun showBotGame()
    fun showMenu()
    fun showRecords()
    fun goToAuthScreen()
}
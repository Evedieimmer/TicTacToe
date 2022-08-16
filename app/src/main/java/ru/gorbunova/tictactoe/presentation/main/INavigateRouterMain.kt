package ru.gorbunova.tictactoe.presentation.main

import androidx.fragment.app.Fragment

interface INavigateRouterMain {
    fun showGame()
    fun showMenu()
    fun showRecords()
    fun goToAuthScreen()
}
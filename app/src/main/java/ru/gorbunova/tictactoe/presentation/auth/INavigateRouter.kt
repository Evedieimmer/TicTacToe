package ru.gorbunova.tictactoe.presentation.auth

import android.view.View

interface INavigateRouter {
    fun showSignUp()
    fun showSignIn()
    fun goToMenuScreen()
    fun showLoad()
}
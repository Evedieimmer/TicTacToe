package ru.gorbunova.tictactoe.presentation.auth.signIn

import ru.gorbunova.tictactoe.base.IBaseView

interface ISignInView : IBaseView{
    fun showError(message: String?)
    fun goToMenu()
}
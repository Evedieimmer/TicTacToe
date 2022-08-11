package ru.gorbunova.tictactoe.presentation.auth.signUp


import ru.gorbunova.tictactoe.base.IBaseView

interface ISignUpView: IBaseView {
    fun showError(message: String?)
    fun showSignInScreen()
}
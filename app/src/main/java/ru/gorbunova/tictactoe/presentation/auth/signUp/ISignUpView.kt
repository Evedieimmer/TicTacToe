package ru.gorbunova.tictactoe.presentation.auth.signUp

import moxy.MvpView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SkipStrategy::class)
interface ISignUpView : MvpView {
    fun showError(message: String)
    fun showSignInScreen()
}
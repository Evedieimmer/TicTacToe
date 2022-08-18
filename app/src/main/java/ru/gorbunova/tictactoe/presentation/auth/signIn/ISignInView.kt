package ru.gorbunova.tictactoe.presentation.auth.signIn

import moxy.MvpView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SkipStrategy::class)
interface ISignInView : MvpView {
    fun showError(message: String)
    fun goToMenu()
}
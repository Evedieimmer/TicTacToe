package ru.gorbunova.tictactoe.presentation.main.menu

import moxy.MvpView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SkipStrategy::class)
interface IMenuView : MvpView {
    fun goToAuthScreen()
}
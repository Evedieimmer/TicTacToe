package ru.gorbunova.tictactoe.presentation.auth.load

import moxy.MvpView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SkipStrategy::class)
interface ILoadView : MvpView {
    fun onLoadingComplete()
    fun onLoadingCompleteLogging()
}
package ru.gorbunova.tictactoe.presentation.main.records

import moxy.MvpView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SkipStrategy::class)
interface IRecordsView : MvpView {
}
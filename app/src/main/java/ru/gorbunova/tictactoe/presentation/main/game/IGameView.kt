package ru.gorbunova.tictactoe.presentation.main.game

import moxy.MvpView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SkipStrategy::class)
interface IGameView : MvpView {
}
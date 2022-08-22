package ru.gorbunova.tictactoe.presentation.main.game

import moxy.MvpView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SkipStrategy::class)
interface IGameView : MvpView {
   fun changeCell(cellIndex: Int, stateCell: Int)
   fun openWinDialog(nameWinner: String)
   fun initBoard()
}
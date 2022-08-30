package ru.gorbunova.tictactoe.presentation.main.game

import moxy.MvpView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.gorbunova.tictactoe.gameLogic.networkGame.NetworkPlayer

@StateStrategyType(SkipStrategy::class)
interface INetworkGameView : MvpView {
    fun goToAuth()
}
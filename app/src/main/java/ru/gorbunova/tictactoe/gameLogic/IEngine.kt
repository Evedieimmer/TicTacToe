package ru.gorbunova.tictactoe.gameLogic

import android.media.AsyncPlayer
import ru.gorbunova.tictactoe.presentation.main.game.GamePresenter

interface IEngine {
    fun addListener(l: (IGameState) -> Unit)
    fun initGame()
    fun addPlayer(player: IPlayer)
    fun ready(player: IPlayer)
    fun executeMove(player: IPlayer, index: Int)
}
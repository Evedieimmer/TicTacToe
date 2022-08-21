package ru.gorbunova.tictactoe.gameLogic

import android.media.AsyncPlayer
import ru.gorbunova.tictactoe.presentation.main.game.GamePresenter

interface IEngine {
    fun addListener(l: (Int, Int) -> Unit)
    fun initGame()
    fun addPlayer(player: IPlayer)
    fun ready(player: IPlayer)
    fun executeMove(player: IPlayer, indexCell: Int)
}
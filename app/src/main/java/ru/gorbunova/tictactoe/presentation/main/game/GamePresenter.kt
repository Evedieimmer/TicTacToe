package ru.gorbunova.tictactoe.presentation.main.game

import moxy.MvpPresenter
import ru.gorbunova.tictactoe.gameLogic.GameEngineLocal
import ru.gorbunova.tictactoe.gameLogic.LocalPlayer
import javax.inject.Inject

class GamePresenter @Inject constructor() : MvpPresenter<IGameView>() {

    private var gameEngineLocal = GameEngineLocal()
    private var player1 = LocalPlayer("Игрок 1", 0)
    private var player2 = LocalPlayer("Игрок 2", 0)

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        gameEngineLocal.addListener { cellIndex, actionType ->
            viewState.changeCell(cellIndex, actionType)
        }
    }

    private fun addPlayerToGameAndGiveRole() {
        gameEngineLocal.addPlayer(player1)
        gameEngineLocal.addPlayer(player2)
    }

    fun clickOnCell(indexCell: Int){
        val currentPlayer = if (player1.action) player1 else player2
        gameEngineLocal.executeMove(currentPlayer, indexCell)
    }

}

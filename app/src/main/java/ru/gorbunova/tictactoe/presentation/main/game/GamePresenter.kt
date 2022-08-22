package ru.gorbunova.tictactoe.presentation.main.game

import moxy.MvpPresenter
import ru.gorbunova.tictactoe.gameLogic.GameEngineLocal
import ru.gorbunova.tictactoe.gameLogic.IEngine
import ru.gorbunova.tictactoe.gameLogic.IPlayer
import ru.gorbunova.tictactoe.gameLogic.LocalPlayer
import javax.inject.Inject

class GamePresenter @Inject constructor() : MvpPresenter<IGameView>() {

    private lateinit var gameEngineLocal: IEngine
    private var player1 = LocalPlayer("Игрок 1", 0)
    private var player2 = LocalPlayer("Игрок 2", 0)
    private var winnerName: String? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        gameEngineLocal = GameEngineLocal().apply {
            initGame()
        }

        addPlayerToGameAndGiveRole()

        gameEngineLocal.addListener { gameState ->
//            viewState.changeCell()
            render(gameState.getCells())

            gameState.getWinner()?.apply {
                winnerName = this
//                viewState.openWinDialog(winnerName)
            }
        }
    }

    fun render(cellsState: IntArray) {
        //заполнение списка кнопок значениями массива из движка
    }

    fun isWinnerExist(winnerName: String): String {
        return "Победил: $winnerName"
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

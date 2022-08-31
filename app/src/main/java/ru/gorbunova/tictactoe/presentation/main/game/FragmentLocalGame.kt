package ru.gorbunova.tictactoe.presentation.main.game

import android.os.Bundle
import android.view.View
import ru.gorbunova.tictactoe.gameLogic.*
import ru.gorbunova.tictactoe.gameLogic.IEngine
import ru.gorbunova.tictactoe.gameLogic.localGame.LocalPlayer

class FragmentLocalGame: AFragmentGame() {

//    private val listener: (IEngine) -> Unit = { engine ->
//
//        val state = engine.getState()
//        val winner = state.getWinner()
//        if (winner != null) onWinner(winner)
//        else if (engine.isGameOver()) onGameOver()
//        else {
//            engine.getPlayer1().also {
//                renderPlayer1(it)
//            }
//            engine.getPlayer2()?.also {
//                renderPlayer2(it)
//            }
//        }
//        renderCells(state)
//    }
//
//    override fun provideListener() = listener

    override fun createEngine() = ServiceGame.createLocalGame()

    override fun createPlayers(engine: IEngine) {
        val player1 = LocalPlayer("Игрок 1")
        val player2 = LocalPlayer("Игрок 2")
        engine.addPlayer(player1)
        engine.addPlayer(player2)
//        player1.ready()
//        player2.ready()
    }

    override fun isPlayerReady(engine: IEngine): Boolean {
        engine.getPlayer1().ready()
        engine.getPlayer2()?.ready()
        return true
    }
}
package ru.gorbunova.tictactoe.presentation.main.game

import kotlinx.android.synthetic.main.fragment_game.*
import ru.gorbunova.tictactoe.gameLogic.*
import ru.gorbunova.tictactoe.gameLogic.ServiceGame.LOCAL_GAME


class FragmentLocalGame: FragmentGame() {

    private val listener: (IEngine) -> Unit = { engine ->

        val state = engine.getState()
        val winner = state.getWinner()
        if (winner != null) onWinner(winner)
        else {
            engine.getPlayer1().also {
                renderPlayer1(it)
            }
            engine.getPlayer2()?.also {
                renderPlayer2(it)
            }
            renderCells(state)
        }
    }

    override fun provideListener() = listener

    override fun createEngine() = GameEngineLocal()

    override fun createPlayers(engine: IEngine) {
        val player1 = LocalPlayer("Игрок 1")
        val player2 = LocalPlayer("Игрок 2")
        engine.addPlayer(player1)
        engine.addPlayer(player2)
        player1.ready()
        player2.ready()
    }
}
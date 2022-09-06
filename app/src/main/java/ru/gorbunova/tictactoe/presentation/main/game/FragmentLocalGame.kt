package ru.gorbunova.tictactoe.presentation.main.game

import android.os.Bundle
import android.view.View
import ru.gorbunova.tictactoe.gameLogic.*
import ru.gorbunova.tictactoe.gameLogic.IEngine
import ru.gorbunova.tictactoe.gameLogic.localGame.LocalPlayer

class FragmentLocalGame: AFragmentGame() {

    override fun createEngine() = ServiceGame.createLocalGame()

    override fun createPlayers(engine: IEngine) {
        val player1 = LocalPlayer("Лама")
        val player2 = LocalPlayer("Панда")
        engine.addPlayer(player1)
        engine.addPlayer(player2)
        player1.ready()
        player2.ready()
    }
}
package ru.gorbunova.tictactoe.presentation.main.game

import android.os.Bundle
import android.view.View
import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import ru.gorbunova.tictactoe.gameLogic.*
import ru.gorbunova.tictactoe.gameLogic.IEngine
import ru.gorbunova.tictactoe.gameLogic.localGame.LocalPlayer

class FragmentLocalGame: AFragmentGame() {

    companion object {

        private const val KEY_WITH_BOT = "KEY_WITH_BOT"

        fun create(withBot: Boolean = false) = FragmentLocalGame().apply {
            arguments = Bundle().apply {
                putBoolean(KEY_WITH_BOT, withBot)
            }
        }
    }

    override fun createEngine() = ServiceGame.createLocalGame()

    override fun createPlayers(engine: IEngine) {
        if (isWithBot()) createWithBot(engine)
        else createTwoPlayers(engine)
    }

    private fun createTwoPlayers(engine: IEngine) {
        val player1 = LocalPlayer("Лама")
        val player2 = LocalPlayer("Панда")
        engine.addPlayer(player1)
        engine.addPlayer(player2)
        player1.ready()
        player2.ready()
    }

    private fun createWithBot(engine: IEngine) {
        val player1 = LocalPlayer("Лама")
        val player2 = BotPlayer(User("Bot", ""))
        engine.addPlayer(player1)
        engine.addPlayer(player2)
        player1.ready()
    }

    private fun isWithBot(): Boolean = arguments?.getBoolean(KEY_WITH_BOT) ?: false
}
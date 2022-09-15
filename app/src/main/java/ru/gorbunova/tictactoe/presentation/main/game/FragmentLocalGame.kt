package ru.gorbunova.tictactoe.presentation.main.game

import android.os.Bundle
import eac.common.Tools
import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import ru.gorbunova.tictactoe.gameLogic.*
import ru.gorbunova.tictactoe.gameLogic.base.IEngine
import ru.gorbunova.tictactoe.gameLogic.localGame.LocalPlayer
import ru.gorbunova.tictactoe.gameLogic.localServerGame.FakePlayer
import java.lang.IllegalStateException

class FragmentLocalGame: AFragmentGame() {

    companion object {

        private const val ARG_MODE = "ARG_MODE"
        private const val ARG_PORT = "ARG_PORT"

        private const val MODE_TWO_PLAYERS = 0
        private const val MODE_WITH_BOT = 1
        private const val MODE_WITH_SERVER = 2

        fun createWithBot() = FragmentLocalGame().apply {
            arguments = Bundle().apply {
                putInt(ARG_MODE, MODE_WITH_BOT)
            }
        }

        fun createWithServer(port: Int) = FragmentLocalGame().apply {
            arguments = Bundle().apply {
                putInt(ARG_MODE, MODE_WITH_SERVER)
                putInt(ARG_PORT, port)
            }
        }
    }

    override fun createEngine(): IEngine {
        return if (getMode() == MODE_WITH_SERVER)
            ServiceGame.createServerGame(getPort())
        else
            ServiceGame.createLocalGame()
    }

    override fun createPlayers(engine: IEngine) {
        when (getMode()) {
            MODE_WITH_BOT -> createWithBot(engine)
            MODE_WITH_SERVER -> createWithServer(engine)
            else -> createTwoPlayers(engine)
        }
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

    private fun createWithServer(engine: IEngine) {
        val player1 = LocalPlayer("Лама")
        engine.addPlayer(player1)
        player1.ready()

        toast(Tools.getInetAddress()?.hostAddress ?: "localhost")
    }

    private fun getMode() = arguments?.getInt(ARG_MODE) ?: MODE_TWO_PLAYERS
    private fun getPort() = arguments?.getInt(ARG_PORT) ?: 8080
}
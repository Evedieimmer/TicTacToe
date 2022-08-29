package ru.gorbunova.tictactoe.presentation.main.game

import ru.gorbunova.tictactoe.gameLogic.IEngine

class FragmentNetworkGame : FragmentGame() {

    private val listener: (IEngine) -> Unit = { engine ->

    }

    override fun createEngine(): IEngine {
        TODO("Not yet implemented")
    }

    override fun createPlayers(engine: IEngine) {
        TODO("Not yet implemented")
    }

    override fun provideListener() = listener
}
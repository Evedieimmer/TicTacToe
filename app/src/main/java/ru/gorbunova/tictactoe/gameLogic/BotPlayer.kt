package ru.gorbunova.tictactoe.gameLogic

import ru.gorbunova.tictactoe.gameLogic.APlayer
import ru.gorbunova.tictactoe.gameLogic.IEngine
import ru.gorbunova.tictactoe.gameLogic.IGameState.Companion.GAME_CELL_VALUE_NONE
import ru.gorbunova.tictactoe.gameLogic.IPlayer


class BotPlayer(
    private val name: String
) : APlayer() {

    override fun getName() = name

    override fun setEngine(engine: IEngine) {
        super.setEngine(engine)
        engine.addListener {

        }
    }
}
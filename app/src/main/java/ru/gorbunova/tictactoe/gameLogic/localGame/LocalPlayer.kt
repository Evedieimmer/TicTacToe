package ru.gorbunova.tictactoe.gameLogic.localGame

import ru.gorbunova.tictactoe.gameLogic.APlayer
import ru.gorbunova.tictactoe.gameLogic.IEngine
import ru.gorbunova.tictactoe.gameLogic.IGameState.Companion.GAME_CELL_VALUE_NONE
import ru.gorbunova.tictactoe.gameLogic.IPlayer


class LocalPlayer(
    private val name: String
) : APlayer() {

    override fun getName() = name
}
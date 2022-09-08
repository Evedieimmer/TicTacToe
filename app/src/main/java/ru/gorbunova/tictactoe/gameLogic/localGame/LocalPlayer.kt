package ru.gorbunova.tictactoe.gameLogic.localGame

import ru.gorbunova.tictactoe.gameLogic.base.APlayer


class LocalPlayer(
    private val name: String
) : APlayer() {

    override fun getName() = name
}
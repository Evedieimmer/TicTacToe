package ru.gorbunova.tictactoe.gameLogic

import ru.gorbunova.tictactoe.gameLogic.IPlayer.Companion.GAME_CELL_VALUE_NONE

class LocalPlayer(override val namePlayer: String, override val score: Int) : IPlayer {

    private val actionType = GAME_CELL_VALUE_NONE

    override fun startPlay() {

    }

    override fun ready() {

    }

    override fun exitGame() {
        TODO("Not yet implemented")
    }

    override fun render() {
        TODO("Not yet implemented")
    }

    override fun getActionType(): Int {
        return actionType
    }

}
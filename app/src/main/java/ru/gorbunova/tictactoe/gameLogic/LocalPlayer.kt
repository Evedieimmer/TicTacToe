package ru.gorbunova.tictactoe.gameLogic

import ru.gorbunova.tictactoe.gameLogic.IGameState.Companion.GAME_CELL_VALUE_CROSS
import ru.gorbunova.tictactoe.gameLogic.IGameState.Companion.GAME_CELL_VALUE_NONE
import ru.gorbunova.tictactoe.gameLogic.IGameState.Companion.GAME_CELL_VALUE_ZERO


class LocalPlayer(
    override val namePlayer: String,
    override var score: Int?
) : IPlayer {

    override var action: Boolean = false
    private var actionType = GAME_CELL_VALUE_NONE
    var isReady: Boolean = false

    override fun ready() {
        isReady = true
    }

    override fun getActionType(): Int {
        return actionType
    }

    override fun initPlayer(isFirst: Boolean): LocalPlayer {
        action = isFirst
        actionType = if( isFirst) GAME_CELL_VALUE_CROSS else GAME_CELL_VALUE_ZERO

        return this
    }
}
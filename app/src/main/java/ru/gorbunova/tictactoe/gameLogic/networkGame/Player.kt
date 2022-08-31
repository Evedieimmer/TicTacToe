package ru.gorbunova.tictactoe.gameLogic.networkGame

import ru.gorbunova.tictactoe.gameLogic.IEngine
import ru.gorbunova.tictactoe.gameLogic.IPlayer

class RemotePlayer(
    private val userId: Int,
    private val userLogin: String,

    val action: Boolean,
    private val actionType: Int,
    private val winCounter: Int,
    private val isOnline: Boolean,
    private val isReady: Boolean
) : IPlayer {

    override fun getActionType(): Int = actionType

    override fun setActionType(value: Int) {}

    override fun setEngine(engine: IEngine) {}

    override fun onWin() {}

    override fun ready() {}

    override fun executeMove(indexCell: Int) {}

    override fun getScore(): Int = winCounter


    override fun getId(): Int = userId

    override fun getName(): String = userLogin

    override fun isOnline(): Boolean = isOnline

    override fun isReady(): Boolean = isReady
}

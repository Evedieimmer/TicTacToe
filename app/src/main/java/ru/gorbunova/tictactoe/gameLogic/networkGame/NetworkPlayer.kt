package ru.gorbunova.tictactoe.gameLogic.networkGame

import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import ru.gorbunova.tictactoe.gameLogic.IEngine
import ru.gorbunova.tictactoe.gameLogic.IPlayer

class NetworkPlayer(
    private val user: User
): IPlayer {

    override fun getActionType(): Int {
        TODO("Not yet implemented")
    }

    override fun setActionType(value: Int) {
        TODO("Not yet implemented")
    }

    override fun setEngine(engine: IEngine) {

    }

    override fun onWin() {
        TODO("Not yet implemented")
    }

    override fun ready() {
        TODO("Not yet implemented")
    }

    override fun executeMove(indexCell: Int) {
        TODO("Not yet implemented")
    }

    override fun getScore(): Int {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        TODO("Not yet implemented")
    }

    fun GetPlayerToken(): String{

        return ""
    }
}
package ru.gorbunova.tictactoe.gameLogic.networkGame

import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import ru.gorbunova.tictactoe.gameLogic.APlayer
import ru.gorbunova.tictactoe.gameLogic.IReadyPlayer

class NetworkPlayer(
    private val user: User
): APlayer(), IReadyPlayer {

    private var ready: Boolean = false

    override fun getId(): Int = user.id

    override fun getName(): String = user.login

    override fun ready() {
        ready = true
        super.ready()
    }

    override fun isReady(): Boolean = ready

    override fun setReady(value: Boolean) {
        ready = value
    }
}
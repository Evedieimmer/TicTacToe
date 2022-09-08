package ru.gorbunova.tictactoe.gameLogic.localServer

import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import ru.gorbunova.tictactoe.gameLogic.networkGame.NetworkPlayer

class FakePlayer(
    private val user: User
): NetworkPlayer(user) {
}
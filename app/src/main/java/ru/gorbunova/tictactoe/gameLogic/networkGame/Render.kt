package ru.gorbunova.tictactoe.gameLogic.networkGame

data class RemoteState(
    var status: Int,
    var game: IntArray,
    var players: List<RemotePlayer>,
    var winner: RemotePlayer
)

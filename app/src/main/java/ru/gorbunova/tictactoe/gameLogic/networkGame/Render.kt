package ru.gorbunova.tictactoe.gameLogic.networkGame

data class RemoteState(
    var status: Int,
    var game: IntArray,
    var player: List<RemotePlayer>,
    var winner: RemotePlayer
)

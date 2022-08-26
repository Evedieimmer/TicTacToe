package ru.gorbunova.tictactoe.gameLogic.networkGame

data class RemotePlayer(
    val userId: Int,
    val userLogin: String,
    var action: Boolean,
    var actionType: Int,
    var winCounter: Int,
    var isOnline: Boolean,
    var isReady: Boolean
)

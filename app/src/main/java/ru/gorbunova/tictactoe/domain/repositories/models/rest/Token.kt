package ru.gorbunova.tictactoe.domain.repositories.models.rest

data class Token(
    val access: String,
    var refresh: String
)

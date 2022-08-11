package ru.gorbunova.tictactoe.domain.repositories.models.rest

data class Token(
    val access: String,
    val refresh: String
)

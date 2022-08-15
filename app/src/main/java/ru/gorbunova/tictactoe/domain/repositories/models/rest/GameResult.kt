package ru.gorbunova.tictactoe.domain.repositories.models.rest

import com.google.gson.annotations.SerializedName

data class GameResult(
    val login: String,
    val value: Int
)
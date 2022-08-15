package ru.gorbunova.tictactoe.domain.repositories.models.rest

import com.google.gson.annotations.SerializedName

data class GameScore(
    @SerializedName("game_tag")
    val gameTag: String? = null,
    val value: Int? = 0
)
package ru.gorbunova.tictactoe.domain.repositories.models.rest


import com.google.gson.annotations.SerializedName

data class User(
    var login: String,
    var password: String,

    val id: Int = 0,
    @SerializedName("avatar_url")
    val avatarUrl: String? = null,
    var token: Token? = null
)


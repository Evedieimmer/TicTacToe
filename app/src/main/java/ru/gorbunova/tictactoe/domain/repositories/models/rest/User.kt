package ru.gorbunova.tictactoe.domain.repositories.models.rest


import com.google.gson.annotations.SerializedName

data class User(
    var login: String = "",
    var password: String = "",

    @SerializedName("avatar_url")
    var avatarUrl: String? = null,

    val id: Int = 0,
    var token: Token? = null
)


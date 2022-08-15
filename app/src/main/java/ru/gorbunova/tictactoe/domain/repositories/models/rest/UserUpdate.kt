package ru.gorbunova.tictactoe.domain.repositories.models.rest

import com.google.gson.annotations.SerializedName

data class UserUpdate (
    @SerializedName("new_password")
    val newPassword: String,
    @SerializedName("old_password")
    val oldPassword: String,
    @SerializedName("password_success")
    val passwordSuccess: String,
    @SerializedName("avatar_success")
    val avatarSuccess: Boolean? = true,
    @SerializedName("new_avatar_url")
    val newAvatarUrl: String? = null
        )
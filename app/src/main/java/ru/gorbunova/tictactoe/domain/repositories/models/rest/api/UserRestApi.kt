package ru.gorbunova.tictactoe.domain.repositories.models.rest.api

import okhttp3.MultipartBody
import ru.gorbunova.tictactoe.domain.di.module.NetModule
import ru.gorbunova.tictactoe.domain.repositories.models.rest.GameScore
import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import ru.gorbunova.tictactoe.domain.repositories.models.rest.UserUpdate
import ru.gorbunova.tictactoe.domain.repositories.models.rest.service.IUserRestApiService
import soft.eac.appmvptemplate.common.net.ABaseRestApi
import soft.eac.appmvptemplate.common.net.IRestClient
import javax.inject.Inject
import javax.inject.Named

class UserRestApi : ABaseRestApi<IUserRestApiService> {
    @Inject
    constructor(@Named(NetModule.NAME_AUTH_REST_CLIENT) client: IRestClient) : super(client)

    fun registration(login: String, password: String) = api.registration(
        User(login, password)
    )

//    fun registration(login: String, password: String, avatarUrl: String) = api.registration(User(login, password, avatarUrl))

    fun login(login: String, password: String) = api.login(
        User(login, password)
    )

    fun refreshToken(refreshToken: String) = api.refreshToken(refreshToken)

    fun logOut(accessToken: String) = api.logOut(accessToken)

    fun updateUser(
        newPassword: String,
        oldPassword: String,
        passwordSuccess: String
    ) = api.updateUser(
        UserUpdate(newPassword, oldPassword, passwordSuccess)
    )

    fun saveGameResult(
        gameScore: GameScore
    ) = api.saveGameResult(
        gameScore
    )

    fun getGameResultTable(
        gameTag: Int
    ) = api.getGameResultTable(gameTag)

    fun uploadAvatar(avatar: MultipartBody.Part, accessToken: String) = api.uploadAvatar(accessToken, avatar)
}
package ru.gorbunova.tictactoe.domain.repositories.models.rest.api

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

    fun login(login: String, password: String) = api.login(
        User(login, password)
    )

    fun refreshToken(refreshToken: String) = api.refreshToken(refreshToken)

    fun logOut(accessToken: String) = api.logOut(accessToken)

    fun updateUser(
        accessToken: String,
        newPassword: String,
        oldPassword: String,
        passwordSuccess: String
    ) = api.updateUser(
        accessToken,
        UserUpdate(newPassword, oldPassword, passwordSuccess)
    )

    fun saveGameResult(
        accessToken: String,
        gameScore: GameScore
    ) = api.saveGameResult(
        accessToken,
        gameScore
    )

    fun getGameResultTable(
        accessToken: String,
        gameTag: Int
    ) = api.getGameResultTable(accessToken, gameTag)

}
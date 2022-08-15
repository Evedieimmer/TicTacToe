package ru.gorbunova.tictactoe.domain.repositories.models.rest.api

import ru.gorbunova.tictactoe.base.ABaseRestApi
import ru.gorbunova.tictactoe.base.IRestClient
import ru.gorbunova.tictactoe.domain.di.module.NetModule
import ru.gorbunova.tictactoe.domain.repositories.models.rest.GameScore
import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import ru.gorbunova.tictactoe.domain.repositories.models.rest.UserUpdate
import ru.gorbunova.tictactoe.domain.repositories.models.rest.service.IUserRestApiService
import javax.inject.Inject
import javax.inject.Named

class UserRestApi : ABaseRestApi<IUserRestApiService> {
    @Inject
    constructor(@Named(NetModule.NAME_AUTH_REST_CLIENT) client: IRestClient) : super(client)

    fun registration(login: String, password: String) = service.registration(
        User(login, password)
    )

    fun login(login: String, password: String) = service.login(
        User(login, password)
    )

    fun refreshToken(refreshToken: String) = service.refreshToken(refreshToken)

    fun logOut(accessToken: String) = service.logOut(accessToken)

    fun updateUser(
        accessToken: String,
        newPassword: String,
        oldPassword: String,
        passwordSuccess: String
    ) = service.updateUser(
        accessToken,
        UserUpdate(newPassword, oldPassword, passwordSuccess)
    )

    fun saveGameResult(
        accessToken: String,
        gameScore: GameScore
    ) = service.saveGameResult(
        accessToken,
        gameScore
    )

    fun getGameResultTable(
        accessToken: String,
        gameTag: Int
    ) = service.getGameResultTable(accessToken, gameTag)

}
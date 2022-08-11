package ru.gorbunova.tictactoe.domain.repositories.models.rest.api

import ru.gorbunova.tictactoe.base.ABaseRestApi
import ru.gorbunova.tictactoe.base.IRestClient
import ru.gorbunova.tictactoe.domain.di.module.NetModule
import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import ru.gorbunova.tictactoe.domain.repositories.models.rest.service.IAuthRestApiService
import javax.inject.Inject
import javax.inject.Named

class UserRestApi : ABaseRestApi<IAuthRestApiService> {
    @Inject
    constructor(@Named(NetModule.NAME_AUTH_REST_CLIENT) client: IRestClient) : super(client)

    fun registration(login: String, password: String) = service.registration(
        User(login = login, password = password)
    )

    fun login(login: String, password: String) = service.login(
      User(login = login, password = password)
    )

}
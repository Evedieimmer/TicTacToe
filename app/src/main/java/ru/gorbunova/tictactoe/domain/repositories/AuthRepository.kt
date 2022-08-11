package ru.gorbunova.tictactoe.domain.repositories

import ru.gorbunova.tictactoe.base.SubRX
import ru.gorbunova.tictactoe.base.standardSubscribeIO
import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import ru.gorbunova.tictactoe.domain.repositories.models.rest.api.UserRestApi
import javax.inject.Inject

class AuthRepository {
    //    private val storage: UserStorage
    private val rest: UserRestApi

    @Inject
    constructor(rest: UserRestApi) {
//        this.storage = storage
        this.rest = rest
    }

    fun login(observer: SubRX<User>, login: String, pass: String) {

        rest.login(login, pass).doOnNext { }.doOnError { }.standardSubscribeIO(observer)
    }

    fun registration(observer: SubRX<User>, login: String, pass: String) {

        rest.registration(login, pass).doOnNext { }.doOnError { }.standardSubscribeIO(observer)
    }
}
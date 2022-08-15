package ru.gorbunova.tictactoe.domain.repositories

import android.os.SystemClock
import ru.gorbunova.tictactoe.base.SubRX
import ru.gorbunova.tictactoe.base.standardSubscribeIO
import ru.gorbunova.tictactoe.domain.repositories.local.UserStorage
import ru.gorbunova.tictactoe.domain.repositories.models.rest.Response
import ru.gorbunova.tictactoe.domain.repositories.models.rest.Token
import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import ru.gorbunova.tictactoe.domain.repositories.models.rest.api.UserRestApi
import java.net.HttpURLConnection
import javax.inject.Inject

class UserRepository {
    private val storage: UserStorage
    private val rest: UserRestApi

    @Inject
    constructor(storage: UserStorage, rest: UserRestApi) {
        this.storage = storage
        this.rest = rest
    }

    fun login(observer: SubRX<User>, login: String, pass: String) {

        rest.login(login, pass).doOnNext {
            storage.save(it, login, pass)
        }.doOnError { }.standardSubscribeIO(observer)
    }

    fun registration(observer: SubRX<User>, login: String, pass: String) {

        rest.registration(login, pass).doOnNext {
            storage.save(it, login, pass)
        }.doOnError { }.standardSubscribeIO(observer)
    }

    fun refreshToken(
        token: Token,
        onRetry: (Int) -> Boolean = { it != HttpURLConnection.HTTP_UNAUTHORIZED }
    ): Token? {
        val response = rest.refreshToken(token.refresh).execute()
        if (response.isSuccessful)
            response.body()?.let {
                it.refresh = token.refresh
                storage.save(it)
                return it
            }
        if (onRetry(response.code())) {
            SystemClock.sleep(500)
            return refreshToken(token)
        }
        return null
    }
    fun getToken() = storage.getToken()

    fun logOut(observer: SubRX<Response>) {
        getToken()?.access?.let {
            rest.logOut(it).doOnNext { response ->
                if (response.success)
                    storage.clearUserData()
            }.doOnError {  }.standardSubscribeIO(observer)
        }
    }

}
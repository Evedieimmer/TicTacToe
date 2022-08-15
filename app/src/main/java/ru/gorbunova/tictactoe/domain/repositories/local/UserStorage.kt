package ru.gorbunova.tictactoe.domain.repositories.local

import io.realm.Realm
import ru.gorbunova.tictactoe.domain.repositories.models.realm.TokenRealm
import ru.gorbunova.tictactoe.domain.repositories.models.realm.UserRealm
import ru.gorbunova.tictactoe.domain.repositories.models.rest.Token
import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import ru.gorbunova.tictactoe.domain.repositories.models.toBase
import ru.gorbunova.tictactoe.domain.repositories.models.toRealm
import javax.inject.Inject

class UserStorage {
    private var user: User? = null
    private var token: Token? = null

    @Inject
    constructor()

    fun save(_user: User, login: String, pass: String) {
        _user.login = login
        this.user = _user
        if (_user.token != null){
            val user = this.user ?: return
            user.login = _user.login
            user.password = _user.password
            user.token = _user.token
        }

        Realm.getDefaultInstance().use {
            it.executeTransaction {
                it.insertOrUpdate(user.toRealm())
            }
        }
    }

    fun save(token: Token) {
        user?.token = token

        Realm.getDefaultInstance().use {
            it.executeTransaction { realm ->
                it.where(TokenRealm::class.java).findAll().deleteAllFromRealm()
                it.copyToRealm(token.toRealm())
            }
        }
    }

    fun getUser(): User? {
        Realm.getDefaultInstance().use {
            return it.where(UserRealm::class.java).findFirst()?.toBase().apply {
                user = this
            }
        }
    }

    fun getToken(): Token? {
        token?.let {
            return it
        }
        Realm.getDefaultInstance().use {
            return it.where(TokenRealm::class.java).findFirst()?.toBase().apply { token = this }
        }
    }
}
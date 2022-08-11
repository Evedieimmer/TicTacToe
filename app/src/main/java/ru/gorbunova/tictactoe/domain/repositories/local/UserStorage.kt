package ru.gorbunova.tictactoe.domain.repositories.local

import io.realm.Realm
import ru.gorbunova.tictactoe.domain.repositories.models.rest.Token
import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import ru.gorbunova.tictactoe.domain.repositories.models.toRealm
import javax.inject.Inject

class UserStorage {
    private var user : User? = null
    private var token : Token? = null

    @Inject
    constructor()

    fun save(user: User){
        this.user = user
        if(user.token != null)
            token = user.token

        Realm.getDefaultInstance().use{
            it.executeTransaction{
                it.insertOrUpdate(user.toRealm())
            }
        }
    }
}
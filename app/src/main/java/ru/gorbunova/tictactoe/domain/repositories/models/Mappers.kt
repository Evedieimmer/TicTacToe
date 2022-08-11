package ru.gorbunova.tictactoe.domain.repositories.models

import ru.gorbunova.tictactoe.domain.repositories.models.realm.TokenRealm
import ru.gorbunova.tictactoe.domain.repositories.models.realm.UserRealm
import ru.gorbunova.tictactoe.domain.repositories.models.rest.Token
import ru.gorbunova.tictactoe.domain.repositories.models.rest.User

fun Token?.toRealm(): TokenRealm?{
    this ?: return null

    return TokenRealm().let{
        it.access = access
        it.refresh = refresh
        it
    }
}

fun User?.toRealm(): UserRealm?{
    this ?: return null
    return UserRealm().let{
        it.id = id ?: 0
        it.login = login
        it.password = password
        it.avatarUrl = avatarUrl
        it.token = token.toRealm()
        it
    }
}
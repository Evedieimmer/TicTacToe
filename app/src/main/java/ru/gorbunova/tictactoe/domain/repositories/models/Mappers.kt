package ru.gorbunova.tictactoe.domain.repositories.models

import ru.gorbunova.tictactoe.domain.repositories.models.realm.TokenRealm
import ru.gorbunova.tictactoe.domain.repositories.models.realm.UploadedFileRealm
import ru.gorbunova.tictactoe.domain.repositories.models.realm.UserRealm
import ru.gorbunova.tictactoe.domain.repositories.models.rest.Token
import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import ru.gorbunova.tictactoe.domain.repositories.models.rest.service.UploadedFile

fun Token?.toRealm(): TokenRealm? {
    this ?: return null

    return TokenRealm().let {
        it.access = access
        it.refresh = refresh
        it
    }
}

fun TokenRealm?.toBase(): Token? {
    this ?: return null
    return Token(access ?: "", refresh ?: "")
}

fun User?.toRealm(): UserRealm? {
    this ?: return null
    return UserRealm().let {
        it.id = id ?: 0
        it.login = login
        it.password = password
        it.avatarUrl = avatarUrl
        it.token = token.toRealm()
        it
    }
}

fun UserRealm?.toBase(): User?{
    this ?: return null
    return User(login ?: "", password ?: "", id, avatarUrl, token.toBase())
}

fun UploadedFile?.toRealm(): UploadedFileRealm? {
    this ?: return null
    return UploadedFileRealm().let {
        it.path = path
        it
    }
}

fun UploadedFileRealm?.toBase(): UploadedFile? {
    this ?: return null
    return UploadedFile(path ?: "")
}
package ru.gorbunova.tictactoe.domain.repositories.models.realm

import io.realm.RealmObject

open class UploadedFileRealm: RealmObject() {
     var path: String? = null
}
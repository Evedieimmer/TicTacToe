package ru.gorbunova.tictactoe.domain.repositories

import android.os.SystemClock
import okhttp3.MultipartBody

import ru.gorbunova.tictactoe.domain.repositories.local.UserStorage
import ru.gorbunova.tictactoe.domain.repositories.models.rest.Response
import ru.gorbunova.tictactoe.domain.repositories.models.rest.Token
import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import ru.gorbunova.tictactoe.domain.repositories.models.rest.UserUpdate
import ru.gorbunova.tictactoe.domain.repositories.models.rest.api.UserRestApi
import ru.gorbunova.tictactoe.domain.repositories.models.rest.service.UploadedFile
import soft.eac.appmvptemplate.common.Photo
import soft.eac.appmvptemplate.common.rx.SubRX
import soft.eac.appmvptemplate.common.rx.standardIO
import java.net.HttpURLConnection
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val storage: UserStorage,
    private val rest: UserRestApi
) {

    fun login(observer: SubRX<User>, login: String, pass: String) {
        rest.login(login, pass).doOnNext {
            storage.save(it, login, pass)
        }.standardIO(observer)
    }

    fun registration(observer: SubRX<User>, login: String, pass: String) {

        rest.registration(login, pass).doOnNext {
            storage.save(it, login, pass)
        }.doOnError { }.standardIO(observer)
    }

    fun uploadAvatar(observer: SubRX<UploadedFile>, avatar: MultipartBody.Part) {
      getToken()?.access?.let { access ->
          rest.uploadAvatar(avatar, access).doOnNext {
              storage.save(it)
          }.standardIO(observer)
      }
    }

    fun updateUser(observer: SubRX<UserUpdate>, newAvatarUrl: String) {
        getToken()?.access?.let { access ->
            rest.updateUser(access, newAvatarUrl).doOnNext {
                storage.update(it)
            }.standardIO(observer)
        }
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

    fun hasToken() = storage.getToken() != null

    fun logOut(observer: SubRX<Response>) {
        getToken()?.access?.let {
            rest.logOut(it).doOnNext { response ->
                if (response.success)
                    storage.clearUserData()
            }.doOnError {  }.standardIO(observer)
        }
    }

    fun getUser() = storage.getUser()

    fun getUploadedFile() = storage.getUploadedFile()
}
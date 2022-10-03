package ru.gorbunova.tictactoe.presentation.auth.signUp


import android.net.Uri
import moxy.InjectViewState
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.gorbunova.tictactoe.domain.repositories.UserRepository
import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import soft.eac.appmvptemplate.common.rx.SubRX
import soft.eac.appmvptemplate.tools.ABasePresenter
import java.io.File
import javax.inject.Inject

@InjectViewState
class SignUpPresenter @Inject constructor(
    private var userRepository: UserRepository
) : ABasePresenter<ISignUpView>() {

    fun registration(login: String, pass: String) {
        userRepository.registration(SubRX { _, e ->
            if (e != null) {
                e.printStackTrace()
                viewState.showError(e.localizedMessage ?: "error")
                return@SubRX
            }
            viewState.showSignInScreen()
        }, login, pass)
    }


    fun uploadAvatar(imagePath: String) {

        val file = File(imagePath)
        val body = MultipartBody.Part.createFormData("file", file.name, file.asRequestBody("image/jpg".toMediaTypeOrNull()))

        userRepository.uploadAvatar(SubRX {_, e ->
            if(e != null) {
                e.printStackTrace()
                viewState.showError(e.localizedMessage ?: "error")
                return@SubRX
            }
        }, body)
        updateUserAvatar(getAvatarUrl())
    }

    fun getUser(): User {
        return userRepository.getUser() ?: throw IllegalStateException("User not find")
    }

    fun getAvatarUrl(): String {
        return userRepository.getUploadedFile()?.path ?: ""
    }

    fun updateUserAvatar( newAvatarUrl: String? = "") {
        if (newAvatarUrl != null) {
            userRepository.updateUser(SubRX {_, e ->
                if(e != null) {
                    e.printStackTrace()
                    viewState.showError(e.localizedMessage ?: "error")
                    return@SubRX
                }
            }, newAvatarUrl)
        }
    }

}

//загрузка фото:
// 1) зарегать пользователя
// 2) получить токен
// 3) загрузить фото на сервер
// 4) забрать ссылку на фото и сохранить? в локальном хранилище
// 5) обновить информацию о пользователе, добавив ссылочку на фото
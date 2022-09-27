package ru.gorbunova.tictactoe.presentation.auth.signUp


import android.net.Uri
import moxy.InjectViewState
import ru.gorbunova.tictactoe.domain.repositories.UserRepository
import soft.eac.appmvptemplate.common.rx.SubRX
import soft.eac.appmvptemplate.tools.ABasePresenter
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


    fun uploadAvatar(uriAvatar: Uri) {
        val accessToken = userRepository.getToken()?.access ?: ""
        if(accessToken != "")
            userRepository.uploadAvatar(SubRX {_, e ->
                if(e != null) {
                    e.printStackTrace()
                    viewState.showError(e.localizedMessage ?: "error")
                    return@SubRX
                }
            }, uriAvatar)
    }

    fun addUserAvatar() {

    }

}

//загрузка фото:
// 1) зарегать пользователя
// 2) получить токен
// 3) загрузить фото на сервер
// 4) забрать ссылку на фото и сохранить? в локальном хранилище
// 5) обновить информацию о пользователе, добавив ссылочку на фото
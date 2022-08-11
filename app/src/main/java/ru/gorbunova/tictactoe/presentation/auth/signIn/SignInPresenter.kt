package ru.gorbunova.tictactoe.presentation.auth.signIn

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.gorbunova.tictactoe.base.SubRX
import ru.gorbunova.tictactoe.domain.repositories.AuthRepository
import javax.inject.Inject

@InjectViewState
class SignInPresenter : MvpPresenter<ISignInView> {

    @Inject
    constructor(/*userRepository: AuthRepository*/) {
//        this.userRepository = userRepository
    }

    @Inject
    lateinit var userRepository: AuthRepository

    fun auth(login: String, password: String) {
        userRepository.login(SubRX { _, e ->

            if (e != null) {
                e.printStackTrace()
                viewState.showError(e.localizedMessage)
                return@SubRX
            }
            viewState.goToMenu()
        }, login, password)
    }


}
package ru.gorbunova.tictactoe.presentation.auth.signUp

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.gorbunova.tictactoe.base.SubRX
import ru.gorbunova.tictactoe.domain.repositories.UserRepository
import javax.inject.Inject

@InjectViewState
class SignUpPresenter : MvpPresenter<ISignUpView> {
    @Inject
    constructor()

    @Inject
    lateinit var userRepository: UserRepository

    fun registration(login: String, pass: String) {
        userRepository.registration(SubRX { _, e ->
            if (e != null) {
                e.printStackTrace()
                viewState.showError(e.localizedMessage)
                return@SubRX
            }
            viewState.showSignInScreen()
        }, login, pass)
    }

}
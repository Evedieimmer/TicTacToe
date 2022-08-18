package ru.gorbunova.tictactoe.presentation.auth.signUp


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

}
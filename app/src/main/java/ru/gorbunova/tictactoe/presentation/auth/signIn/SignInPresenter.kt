package ru.gorbunova.tictactoe.presentation.auth.signIn


import moxy.InjectViewState
import ru.gorbunova.tictactoe.domain.repositories.UserRepository
import soft.eac.appmvptemplate.common.rx.SubRX
import soft.eac.appmvptemplate.tools.ABasePresenter
import javax.inject.Inject

@InjectViewState
class SignInPresenter : ABasePresenter<ISignInView> {

    @Inject
    constructor(/*userRepository: AuthRepository*/) {
//        this.userRepository = userRepository
    }

    @Inject
    lateinit var userRepository: UserRepository

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
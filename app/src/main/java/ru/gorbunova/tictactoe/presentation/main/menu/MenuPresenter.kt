package ru.gorbunova.tictactoe.presentation.main.menu


import moxy.InjectViewState
import ru.gorbunova.tictactoe.domain.repositories.UserRepository
import soft.eac.appmvptemplate.common.rx.SubRX
import soft.eac.appmvptemplate.tools.ABasePresenter
import javax.inject.Inject

@InjectViewState
class MenuPresenter : ABasePresenter<IMenuView> {
    @Inject
    constructor()

    @Inject
    lateinit var userRepository: UserRepository

    fun logOut(){
        userRepository.logOut(SubRX{ _, e ->
            if (e != null) {
                e.printStackTrace()
                return@SubRX
            }
            viewState.goToAuthScreen()
        })
    }
}
package ru.gorbunova.tictactoe.presentation.main.menu

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.gorbunova.tictactoe.base.SubRX
import ru.gorbunova.tictactoe.domain.repositories.UserRepository
import javax.inject.Inject

@InjectViewState
class MenuPresenter : MvpPresenter<IMenuView> {
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
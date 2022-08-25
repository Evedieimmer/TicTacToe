package ru.gorbunova.tictactoe.presentation.auth.load

import moxy.InjectViewState
import ru.gorbunova.tictactoe.App
import ru.gorbunova.tictactoe.domain.repositories.UserRepository
import soft.eac.appmvptemplate.tools.ABasePresenter
import javax.inject.Inject

@InjectViewState
class LoadPresenter @Inject constructor(
    private var userRepository: UserRepository
) : ABasePresenter<ILoadView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
//        App.handler.postDelayed({
            loadStaticResources()
//        }, 500)
    }

    private fun loadStaticResources() {
        if (userRepository.hasToken())
            viewState.onLoadingCompleteLogging()
        else
            viewState.onLoadingComplete()
    }
}

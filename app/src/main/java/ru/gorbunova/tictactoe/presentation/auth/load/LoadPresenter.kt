package ru.gorbunova.tictactoe.presentation.auth.load


import android.os.Handler
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.gorbunova.tictactoe.App
import ru.gorbunova.tictactoe.domain.repositories.UserRepository
import javax.inject.Inject

@InjectViewState
class LoadPresenter @Inject constructor() : MvpPresenter<ILoadView>() {

    @Inject
    lateinit var userRepository: UserRepository

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        App.handler.postDelayed({
            loadStaticResources()
        }, 500)
    }

    fun loadStaticResources() {
        if (userRepository.hasToken())
            viewState.onLoadingCompleteLogging()
        else
            viewState.onLoadingComplete()
    }
}

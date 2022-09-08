package ru.gorbunova.tictactoe.presentation.main.game

import android.os.Bundle
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.domain.di.module.NetModule
import ru.gorbunova.tictactoe.gameLogic.base.IEngine
import ru.gorbunova.tictactoe.gameLogic.networkGame.GameEngineNetwork
import ru.gorbunova.tictactoe.presentation.main.INavigateRouterMain
import javax.inject.Inject


open class FragmentNetworkGame : AFragmentGame(), INetworkGameView {

    companion object {

        private const val KEY_USE_BOT = "KEY_USE_BOT"

        fun create(useBot: Boolean = false) = FragmentNetworkGame().apply {
            arguments = Bundle().apply {
                putBoolean(KEY_USE_BOT, useBot)
            }
        }
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: NetworkGamePresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    override fun createEngine(): IEngine = GameEngineNetwork(
        NetModule.DOMAIN,
        NetModule.GAME_SERVICE_PORT,
        presenter
    )

    override fun createPlayers(engine: IEngine) {
        if (isUseBot()) presenter.createBot(engine)
        else presenter.createPlayers(engine)
    }

    override fun goToAuth() {
        (activity as? INavigateRouterMain)?.goToAuthScreen()
    }

    override fun goToMenu() {
        (activity as? INavigateRouterMain)?.showMenu()
    }

    private fun isUseBot(): Boolean = arguments?.getBoolean(KEY_USE_BOT) ?: false
}
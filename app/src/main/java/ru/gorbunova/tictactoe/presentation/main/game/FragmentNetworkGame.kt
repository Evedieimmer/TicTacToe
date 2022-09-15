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
        private const val KEY_IP = "KEY_IP"
        private const val KEY_PORT = "KEY_PORT"

        fun createWithBot() = FragmentNetworkGame().apply {
            arguments = Bundle().apply {
                putBoolean(KEY_USE_BOT, true)
            }
        }

        fun create(ip: String, port: Int) = FragmentNetworkGame().apply {
            arguments = Bundle().apply {
                putString(KEY_IP, ip)
                putInt(KEY_PORT, port)
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
        getIp(),
        getPort(),
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

    private fun getIp() = arguments?.getString(KEY_IP) ?: NetModule.DOMAIN
    private fun getPort() = arguments?.getInt(KEY_PORT, NetModule.GAME_SERVICE_PORT)
        ?: NetModule.GAME_SERVICE_PORT
    private fun isUseBot(): Boolean = arguments?.getBoolean(KEY_USE_BOT) ?: false
}
package ru.gorbunova.tictactoe.presentation.main.game

import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.domain.di.module.NetModule
import ru.gorbunova.tictactoe.gameLogic.IEngine
import ru.gorbunova.tictactoe.gameLogic.ServiceGame
import ru.gorbunova.tictactoe.gameLogic.networkGame.GameEngineNetwork
import ru.gorbunova.tictactoe.presentation.main.INavigateRouterMain
import javax.inject.Inject


class FragmentNetworkGame : AFragmentGame(), INetworkGameView {

    @Inject
    @InjectPresenter
    lateinit var presenter: NetworkGamePresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    private val listener: (IEngine) -> Unit = { engine ->


    }

    override fun createEngine(): IEngine = GameEngineNetwork(
        NetModule.DOMAIN,
        NetModule.GAME_SERVICE_PORT,
        presenter
    )

    override fun createPlayers(engine: IEngine) {
        presenter.createPlayers(engine)
    }

//    override fun isPlayerReady(engine: IEngine): Boolean {
//        return engine.getCurrentPlayer()?.isReady() ?: false
//    }

    override fun provideListener() = listener

    override fun goToAuth() {
        (activity as? INavigateRouterMain)?.goToAuthScreen()
    }

}
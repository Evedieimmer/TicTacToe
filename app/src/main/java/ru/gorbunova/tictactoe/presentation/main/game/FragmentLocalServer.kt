package ru.gorbunova.tictactoe.presentation.main.game

import android.os.Bundle
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.gameLogic.base.IEngine
import ru.gorbunova.tictactoe.gameLogic.localGame.LocalPlayer
import ru.gorbunova.tictactoe.gameLogic.localServerGame.FakePlayer
import ru.gorbunova.tictactoe.gameLogic.localServerGame.GameEngineLocalServer
import ru.gorbunova.tictactoe.presentation.main.INavigateRouterMain
import javax.inject.Inject


class FragmentLocalServer: AFragmentGame(), INetworkGameView {

    companion object {

        private const val KEY_CREATE_ROOM = "KEY_CREATE_ROOM"

        fun create(roomCreator: Boolean = false) = FragmentNetworkGame().apply {
            arguments = Bundle().apply {
                putBoolean(KEY_CREATE_ROOM, roomCreator)
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

    override fun createEngine(): IEngine = GameEngineLocalServer(8080)

    override fun createPlayers(engine: IEngine) {
        if(isCreatorRoom()) createLocalPlayer(engine)
//        else presenter.createFakePlayer(engine)
    }
    override fun goToAuth() {
        (activity as? INavigateRouterMain)?.goToAuthScreen()
    }

    override fun goToMenu() {
        (activity as? INavigateRouterMain)?.showMenu()
    }

    private fun createLocalPlayer(engine: IEngine) {
        val player1 = LocalPlayer("Хост")
        engine.addPlayer(player1)
        player1.ready()
    }

    private fun isCreatorRoom(): Boolean = arguments?.getBoolean(KEY_CREATE_ROOM) ?: false

}
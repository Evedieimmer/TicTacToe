package ru.gorbunova.tictactoe.presentation.main.game

import ru.gorbunova.tictactoe.gameLogic.base.IEngine


class FragmentBotGame : FragmentNetworkGame(), INetworkGameView{

//    @Inject
//    @InjectPresenter
//    lateinit var presenter: NetworkGamePresenter
//
//    @ProvidePresenter
//    fun providePresenter() = presenter
//
//    override fun inject() {
//        DaggerAppComponent.create().inject(this)
//    }
//    override fun createEngine(): IEngine = GameEngineNetwork(
//        NetModule.DOMAIN,
//        NetModule.GAME_SERVICE_PORT,
//        presenter
//    )
//
    override fun createPlayers(engine: IEngine) {
//        presenter.createPlayers(engine)
        presenter.createBot(engine)
    }
//
//    override fun goToAuth() {
//        (activity as? INavigateRouterMain)?.goToAuthScreen()
//    }
//
//    override fun goToMenu() { }

}
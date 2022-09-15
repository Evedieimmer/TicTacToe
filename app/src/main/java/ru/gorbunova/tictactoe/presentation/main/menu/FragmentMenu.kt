package ru.gorbunova.tictactoe.presentation.main.menu

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_menu.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.presentation.main.INavigateRouterMain
import soft.eac.appmvptemplate.views.ABaseFragment
import javax.inject.Inject


class FragmentMenu : ABaseFragment(R.layout.fragment_menu), IMenuView {

    @Inject
    @InjectPresenter
    lateinit var presenter: MenuPresenter

//    private val binding: FragmentMenuBinding get() = getViewBinding()

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

//     fun getViewId() = R.layout.fragment_menu

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = presenter.getUserName()
        userName.text = "$name"

        btnOnline.setOnClickListener {
            activity?.let {
                if (it is INavigateRouterMain)
                    it.showNetworkGame()
            }
        }

        btnOnlineBot.setOnClickListener {
            activity?.let {
                if (it is INavigateRouterMain)
                    it.showNetworkGameForBot()
            }
        }

        btnWithFriend.setOnClickListener {
            activity?.let {
                if (it is INavigateRouterMain)
                    it.showLocalGame()
            }
        }

        btnWithBot.setOnClickListener {
            activity?.let {
                if (it is INavigateRouterMain)
                    it.showBotGame()
            }
        }

        btnRating.setOnClickListener {
            activity?.let {
                if (it is INavigateRouterMain)
                    it.showRecords()
            }
        }

        btnQuitTheGame.setOnClickListener {
            activity?.let{
                presenter.logOut()
            }
        }

        btnConnectToGame.setOnClickListener {
            activity?.let {
                if (it is INavigateRouterMain)
                    it.showGameWithHost()
            }
        }

        btnCreateLocalServerGame.setOnClickListener {
            activity?.let {
                if (it is INavigateRouterMain)
                    it.showHostGame()
            }
        }
    }

    override fun goToAuthScreen() {
        (activity as? INavigateRouterMain)?.goToAuthScreen()
    }
}
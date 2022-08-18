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

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

//     fun getViewId() = R.layout.fragment_menu

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnWithFriend.setOnClickListener {
            activity?.let {
                if (it is INavigateRouterMain)
                    it.showGame()
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
    }

    override fun goToAuthScreen() {
        (activity as? INavigateRouterMain)?.goToAuthScreen()
    }
}
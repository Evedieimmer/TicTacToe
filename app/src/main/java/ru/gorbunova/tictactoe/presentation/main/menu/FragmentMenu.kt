package ru.gorbunova.tictactoe.presentation.main.menu

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_menu.*
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.base.ABaseFragment
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent

import ru.gorbunova.tictactoe.presentation.main.INavigateRouterMain
import javax.inject.Inject


class FragmentMenu : ABaseFragment(), IMenuView {

    @Inject
    @InjectPresenter
    lateinit var presenter: MenuPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    override fun getViewId() = R.layout.fragment_menu

//    var mHandler: Handler? = null

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
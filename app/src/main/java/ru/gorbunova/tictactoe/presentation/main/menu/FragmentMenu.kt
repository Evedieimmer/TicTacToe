package ru.gorbunova.tictactoe.presentation.main.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_menu.*
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.base.ABaseFragment
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.presentation.auth.INavigateRouter

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

        btnExit.setOnClickListener {
            activity?.let{
                presenter.logOut()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentMenu().apply {
            }
    }

    override fun goToAuthScreen() {
        (activity as? INavigateRouterMain)?.goToAuthScreen()
    }
}
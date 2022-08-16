package ru.gorbunova.tictactoe.presentation.auth.load

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.base.ABaseFragment
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.presentation.auth.INavigateRouter
import javax.inject.Inject

class FragmentLoad : ABaseFragment(), ILoadView{

    @Inject
    @InjectPresenter
    lateinit var presenter: LoadPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    override fun getViewId() = R.layout.fragment_load

    override fun onLoadingComplete() {
        activity.let {
            if( it is INavigateRouter)
                it.showSignIn()
        }
    }

    override fun onLoadingCompleteLogging() {
        activity.let {
            if (it is INavigateRouter)
                it.goToMenuScreen()
        }
    }


}
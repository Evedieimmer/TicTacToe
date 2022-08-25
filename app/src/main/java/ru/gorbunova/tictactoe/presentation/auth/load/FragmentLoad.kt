package ru.gorbunova.tictactoe.presentation.auth.load


import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.presentation.auth.INavigateRouter
import soft.eac.appmvptemplate.views.ABaseFragment
import javax.inject.Inject

class FragmentLoad : ABaseFragment(R.layout.fragment_load), ILoadView{

    @Inject
    @InjectPresenter
    lateinit var presenter: LoadPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

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
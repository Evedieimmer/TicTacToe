package ru.gorbunova.tictactoe.presentation.main.records

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_game.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.presentation.main.INavigateRouterMain
import soft.eac.appmvptemplate.views.ABaseFragment
import javax.inject.Inject

class FragmentRecordsTable : ABaseFragment(R.layout.fragment_records_table), IRecordsView {

    @Inject
    @InjectPresenter
    lateinit var presenter: RecordsPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

//     fun getViewId() = R.layout.fragment_records_table


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnQuitTheGame.setOnClickListener {
            activity.let {
                if (it is INavigateRouterMain)
                    it.showMenu()
            }
        }
    }
}

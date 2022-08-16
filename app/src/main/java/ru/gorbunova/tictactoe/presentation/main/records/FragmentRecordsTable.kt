package ru.gorbunova.tictactoe.presentation.main.records

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_game.*
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.base.ABaseFragment
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.presentation.main.INavigateRouterMain
import javax.inject.Inject

class FragmentRecordsTable : ABaseFragment(), IRecordsView {

    @Inject
    @InjectPresenter
    lateinit var presenter: RecordsPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    override fun getViewId() = R.layout.fragment_records_table


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

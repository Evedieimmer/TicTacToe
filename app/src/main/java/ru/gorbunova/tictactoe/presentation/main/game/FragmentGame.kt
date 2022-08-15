package ru.gorbunova.tictactoe.presentation.main.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.base.ABaseFragment
import ru.gorbunova.tictactoe.databinding.FragmentGameBinding
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import javax.inject.Inject


class FragmentGame : ABaseFragment(), IGameView {

    private lateinit var binding: FragmentGameBinding

    @Inject
    @InjectPresenter
    lateinit var presenter: GamePresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    override fun getViewId() = R.layout.fragment_game

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentGame().apply {
            }
    }
}
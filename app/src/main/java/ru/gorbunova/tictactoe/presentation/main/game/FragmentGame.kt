package ru.gorbunova.tictactoe.presentation.main.game

import android.graphics.drawable.Icon
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.lifecycle.LifecycleOwner
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_game.*
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.base.ABaseFragment
import ru.gorbunova.tictactoe.databinding.FragmentGameBinding
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.presentation.main.INavigateRouterMain
import javax.inject.Inject


class FragmentGame : ABaseFragment(), IGameView {

    private lateinit var binding: FragmentGameBinding
    private var boardList = mutableListOf<Button>()

    @Inject
    @InjectPresenter
    lateinit var presenter: GamePresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    override fun getViewId() = R.layout.fragment_game

     fun onCreated(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentGameBinding.inflate(layoutInflater)
        initBoard()

        btnQuitTheGame.setOnClickListener{
            activity.let {
                if(it is INavigateRouterMain)
                    it.showMenu()
            }
        }
    }

    private fun initBoard()
    {
        boardList.add(binding.btnGame1)
        boardList.add(binding.btnGame2)
        boardList.add(binding.btnGame3)
        boardList.add(binding.btnGame4)
        boardList.add(binding.btnGame5)
        boardList.add(binding.btnGame6)
        boardList.add(binding.btnGame7)
        boardList.add(binding.btnGame8)
        boardList.add(binding.btnGame9)
    }
}
package ru.gorbunova.tictactoe.presentation.main.game

import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_game.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.databinding.FragmentGameBinding
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.presentation.main.INavigateRouterMain
import soft.eac.appmvptemplate.views.ABaseFragment
import javax.inject.Inject


class FragmentGame : ABaseFragment(R.layout.fragment_game), IGameView {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

//     fun onCreated(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//
//        binding = FragmentGameBinding.inflate(layoutInflater)
//        initBoard()
//
//        btnQuitTheGame.setOnClickListener{
//            activity.let {
//                if(it is INavigateRouterMain)
//                    it.showMenu()
//            }
//        }
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
package ru.gorbunova.tictactoe.presentation.main.game

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_game.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.databinding.FragmentGameBinding
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.gameLogic.IGameState.Companion.GAME_CELL_VALUE_CROSS
import ru.gorbunova.tictactoe.gameLogic.IGameState.Companion.GAME_CELL_VALUE_ZERO
import ru.gorbunova.tictactoe.presentation.main.INavigateRouterMain
import ru.gorbunova.tictactoe.presentation.main.menu.CellState
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

        btnQuitTheGame.setOnClickListener {
            activity.let {
                if (it is INavigateRouterMain)
                    it.showMenu()
            }
        }
        initBoard()
        boardList.forEachIndexed { index, button ->
            button.setOnClickListener {
                presenter.clickOnCell(index)
            }
        }
    }

    override fun changeCell(cellIndex: Int, stateCell: Int) {
        when (stateCell) {
            GAME_CELL_VALUE_ZERO -> {
                boardList[cellIndex].setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_zero,
                    0,
                    0,
                    0
                )
                boardList[cellIndex].isClickable = false
            }
            GAME_CELL_VALUE_CROSS -> {
                boardList[cellIndex].setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_cross,
                    0,
                    0,
                    0
                )
                boardList[cellIndex].isClickable = false
            }
        }
    }

    override fun openWinDialog(nameWinner: String) {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Поздравляем")
                .setMessage("Победитель: $nameWinner")
                .setPositiveButton("Начать заново") { dialog, id ->
                    dialog.cancel()
                }
        }
    }

    private fun initBoard() {
        boardList.add(binding.btnGame1) //0
        boardList.add(binding.btnGame2) //1
        boardList.add(binding.btnGame3) //2
        boardList.add(binding.btnGame4) //3
        boardList.add(binding.btnGame5) //4
        boardList.add(binding.btnGame6) //5
        boardList.add(binding.btnGame7) //6
        boardList.add(binding.btnGame8) //7
        boardList.add(binding.btnGame9) //8
    }
}



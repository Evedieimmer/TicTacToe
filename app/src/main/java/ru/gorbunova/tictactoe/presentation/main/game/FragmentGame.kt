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

//    private lateinit var binding: FragmentGameBinding
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

//        val isWinnerExist: String
//        if(nameWinner != null) isWinnerExist = "Победил: $nameWinner"
//        else isWinnerExist = "Ничья!"

        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Игра окончена")
                .setMessage(nameWinner)
                .setPositiveButton("Начать заново") { dialog, id ->
                    dialog.cancel()
                }
        }
    }

    override fun initBoard() {
        boardList.add(btnGame1)
        boardList.add(btnGame2)
        boardList.add(btnGame3)
        boardList.add(btnGame4)
        boardList.add(btnGame5)
        boardList.add(btnGame6)
        boardList.add(btnGame7)
        boardList.add(btnGame8)
        boardList.add(btnGame9)
    }
}



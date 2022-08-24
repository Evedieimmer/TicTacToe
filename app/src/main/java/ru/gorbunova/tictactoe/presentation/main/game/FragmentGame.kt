package ru.gorbunova.tictactoe.presentation.main.game

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.fragment_game.*
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.gameLogic.*
import ru.gorbunova.tictactoe.presentation.main.INavigateRouterMain
import soft.eac.appmvptemplate.common.childViews
import soft.eac.appmvptemplate.views.ABaseFragment


abstract class FragmentGame : ABaseFragment(R.layout.fragment_game) {

//    private lateinit var binding: FragmentGameBinding
    private var boardList = mutableListOf<Button>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val engine = ServiceGame.engine
        if (engine == null) startGame()
        else resumeGame(engine)

        btnQuitTheGame.setOnClickListener {
            activity.let {
                if (it is INavigateRouterMain)
                    it.showMenu()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ServiceGame.engine?.removeListener(provideListener())
    }

    fun renderPlayer1(player: IPlayer) = renderPlayer(llPlayer1, player)

    fun renderPlayer2(player: IPlayer) = renderPlayer(llPlayer2, player)

    fun onWinner(player: IPlayer) {
//        val isWinnerExist: String
//        if(nameWinner != null) isWinnerExist = "Победил: $nameWinner"
//        else isWinnerExist = "Ничья!"

        activity?.let {
            AlertDialog.Builder(it)
                .setTitle("Игра окончена")
                .setMessage(player.getName())
                .setPositiveButton("Начать заново") { dialog, id ->
                    dialog.cancel()
                }
        }
    }

    protected fun renderCells(state: IGameState) {
        state.getCells().onEachIndexed { index, value ->
            changeCell(index, value)
        }
    }

    abstract fun createEngine(): IEngine

    abstract fun createPlayers(engine: IEngine)

    abstract fun provideListener(): (IEngine) -> Unit

    private fun startGame() {
        createEngine().also {
            it.initGame()
            createPlayers(it)
            resumeGame(it)
        }
    }

    private fun resumeGame(engine: IEngine) {
        engine.addListener(provideListener())
        initBoard(engine)
    }


    private fun renderPlayer(view: LinearLayout, player: IPlayer) {
        view.childViews(TextView::class.java)[0].text = player.getName()
    }












    private fun changeCell(cellIndex: Int, stateCell: Int) {
        val btn = boardList[cellIndex]
        val drawableId = when (stateCell) {
            IGameState.GAME_CELL_VALUE_ZERO -> R.drawable.ic_zero
            IGameState.GAME_CELL_VALUE_CROSS -> R.drawable.ic_cross
            else -> R.color.white
        }
        btn.setCompoundDrawablesWithIntrinsicBounds(drawableId, 0, 0, 0)
        btn.isClickable = stateCell != IGameState.GAME_CELL_VALUE_NONE
    }

    private fun initBoard(engine: IEngine) {

        boardList.add(btnGame1)
        boardList.add(btnGame2)
        boardList.add(btnGame3)
        boardList.add(btnGame4)
        boardList.add(btnGame5)
        boardList.add(btnGame6)
        boardList.add(btnGame7)
        boardList.add(btnGame8)
        boardList.add(btnGame9)

        boardList.forEachIndexed { index, button ->
            button.setOnClickListener {
                engine.getCurrentPlayer()
            }
        }
    }
}



package ru.gorbunova.tictactoe.presentation.main.game

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.databinding.FragmentGameBinding
import ru.gorbunova.tictactoe.gameLogic.IEngine
import ru.gorbunova.tictactoe.gameLogic.IGameState
import ru.gorbunova.tictactoe.gameLogic.IPlayer
import ru.gorbunova.tictactoe.gameLogic.ServiceGame
import ru.gorbunova.tictactoe.presentation.main.INavigateRouterMain
import soft.eac.appmvptemplate.common.childViews
import soft.eac.appmvptemplate.views.ABaseFragment


abstract class FragmentGame : ABaseFragment(FragmentGameBinding::class.java) {

    private val binding: FragmentGameBinding get() = getViewBinding()
    private var boardList = mutableListOf<Button>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startGame()

        binding.btnQuitTheGame.setOnClickListener {
            endGame()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ServiceGame.engine?.removeListener(provideListener())
    }

    fun renderPlayer1(player: IPlayer) = renderPlayer(binding.llPlayer1, player)

    fun renderPlayer2(player: IPlayer) = renderPlayer(binding.llPlayer2, player)

    fun onWinner(player: IPlayer) = showDialog(player.getName())

    fun onGameOver() = showDialog("Ничья")

    protected fun renderCells(state: IGameState) {
        state.getCells().onEachIndexed { index, value ->
            changeCell(index, value)
        }
    }

    abstract fun createEngine(): IEngine

    abstract fun createPlayers(engine: IEngine)

    abstract fun provideListener(): (IEngine) -> Unit

    private fun endGame() {
        ServiceGame.endGame()
        activity.let {
            if (it is INavigateRouterMain) {
                it.showMenu()
            }
        }
    }

    private fun createGame() {
        createEngine().also {
            it.initGame()
            createPlayers(it)
            resumeGame(it)
        }
    }

    private fun startGame() {

        val engine = ServiceGame.engine
        if (engine == null)
            createGame()
        else resumeGame(engine)
    }

    private fun restartGame() {
        ServiceGame.restartGame()
    }

    private fun resumeGame(engine: IEngine) {
        initBoard(engine)
        engine.addListener(provideListener())
        engine.addListener {
            binding.score.text = "${engine.getCurrentPlayer()?.getActionType() ?: -1}"
            binding.scoreNum.text = "${engine.getPlayer1().getScore()} | ${engine.getPlayer2()?.getScore() ?: -1}"
        }
    }

//    private fun getTextTurn(engine: IEngine): String {
//         when (engine.getCurrentPlayer()?.getActionType() ?: -1) {
//            0 -> return "нолик"
//            1 -> return "крестик"
//        }
//        return "Error"
//    }


    private fun renderPlayer(view: LinearLayout, player: IPlayer) {
        view.childViews(TextView::class.java)[0].text = "${player.getName()} ${player.getActionType()}"
    }

    private fun changeCell(cellIndex: Int, stateCell: Int) {
        val btn = boardList[cellIndex]
        val drawableId = when (stateCell) {
            IGameState.GAME_CELL_VALUE_ZERO -> R.drawable.ic_zero
            IGameState.GAME_CELL_VALUE_CROSS -> R.drawable.ic_cross
            else -> R.color.white
        }
//        btn.setBackgroundResource(drawableId)
        btn.setCompoundDrawablesWithIntrinsicBounds(drawableId, 0, 0, 0)
        btn.isClickable = stateCell == IGameState.GAME_CELL_VALUE_NONE
    }

    private fun initBoard(engine: IEngine) {

        boardList.clear()
        boardList.add(binding.btnGame1)
        boardList.add(binding.btnGame2)
        boardList.add(binding.btnGame3)
        boardList.add(binding.btnGame4)
        boardList.add(binding.btnGame5)
        boardList.add(binding.btnGame6)
        boardList.add(binding.btnGame7)
        boardList.add(binding.btnGame8)
        boardList.add(binding.btnGame9)

        boardList.forEachIndexed { index, button ->
            button.setOnClickListener {
                engine.getCurrentPlayer()?.executeMove(index) ?: toast("Player not found")
            }
        }
    }

    private fun showDialog(message: String) {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle("Игра окончена")
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton("Снова") { _, _ ->
                    restartGame()
                }
                .setNegativeButton("Выход") { _, _ ->
                    endGame()
                }
                .create()
                .show()
        }
    }
}



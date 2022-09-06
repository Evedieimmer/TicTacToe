package ru.gorbunova.tictactoe.presentation.main.game

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import soft.eac.appmvptemplate.common.visible
import soft.eac.appmvptemplate.views.ABaseFragment


abstract class AFragmentGame : ABaseFragment(FragmentGameBinding::class.java) {

    private val baseListener: (IEngine) -> Unit = { engine ->

        val state = engine.getState()
        val winner = state.getWinner()
        if (winner != null) onWinner(winner)
        else if (engine.isGameOver()) onGameOver()
        else {
            engine.getPlayer1()?.also {
                renderPlayer1(it)
            }
            engine.getPlayer2()?.also {
                renderPlayer2(it)
            }
            engine.getLocalPlayer()?.also {
                binding.btnReady.visible(!it.isReady())
            }
        }
        renderCells(state)
    }

    private val binding: FragmentGameBinding get() = getViewBinding()
    private var boardList = mutableListOf<Button>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startGame()

        binding.btnReady.setOnClickListener {
            ServiceGame.engine?.also { engine ->
                engine.getLocalPlayer()?.ready() ?: toast("Что-то пошло не так")
            }
        }

        binding.btnQuitTheGame.setOnClickListener {
            endGame()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ServiceGame.engine?.also { engine ->
            engine.removeListener(baseListener)
            provideListener()?.also { engine.removeListener(it) }
        }
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

    open fun onError(throwable: Throwable) {
        throwable.printStackTrace()
    }

    abstract fun createEngine(): IEngine

    abstract fun createPlayers(engine: IEngine)

    open fun provideListener(): ((IEngine) -> Unit)? = null

    private fun endGame() {
        ServiceGame.endGame()
        activity.let {
            if (it is INavigateRouterMain) {
                it.showMenu()
            }
        }
    }

    private fun createGame() {
        createEngine().also { engine ->
            ServiceGame.engine = engine
            engine.initGame {
                if (it != null) onError(it)
                else {
                    createPlayers(engine)
                    resumeGame(engine)
                }
            }
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
        engine.addListener(baseListener)
        provideListener()?.also { engine.addListener(it) }
        engine.addListener {
            binding.score.text = "${engine.getActionPlayer()?.getActionType() ?: -1}"
            binding.scoreNum.text =
                "${engine.getPlayer1()?.getScore() ?: -1} | ${engine.getPlayer2()?.getScore() ?: -1}"
//            binding.whoTurn.text = "${engine.getActionPlayer()?.getName() ?: "ошибка"}"
            binding.whoTurn.visibility = if (engine.getActionPlayer() != null) View.VISIBLE else View.INVISIBLE
        }
    }

//    private fun getTextTurn(actionType: Int): String {
//        return when (actionType) {
//            0 -> "нолик"
//            1 -> "крестик"
//             else ->"Error"
//        }
//    }

    private fun renderPlayer(view: LinearLayout, player: IPlayer) {
        view.childViews(TextView::class.java)[0].text =
            "${player.getName()} ${player.getActionType()}"
        view.background = if (player.isOnline() && player.isReady()) ColorDrawable(Color.GREEN) else ColorDrawable(Color.RED)
    }

    private fun changeCell(cellIndex: Int, stateCell: Int) {
        val btn = boardList[cellIndex]
        val drawableId = when (stateCell) {
            IGameState.GAME_CELL_VALUE_ZERO -> R.drawable.ic_zero
            IGameState.GAME_CELL_VALUE_CROSS -> R.drawable.ic_cross
            else -> R.color.white
        }
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
                engine.getActionPlayer()?.executeMove(index) ?: toast("Player not found")
            }
        }
    }

    private var dialog: Dialog? = null
    private fun showDialog(message: String) {
        if (dialog != null)
            return
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
                .setOnDismissListener {
                    dialog = null
                }
                .create().apply { dialog = this }
                .show()
        }
    }
}



package ru.gorbunova.tictactoe.presentation.main.game

import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arellomobile.mvp.MvpPresenter
import ru.gorbunova.tictactoe.R
import javax.inject.Inject

class GamePresenter @Inject constructor() : MvpPresenter<IGameView>() {

    private lateinit var gameMatrix: Array<CellState>
    private lateinit var currentCellState: CellState
    private val mCurrentMove = MutableLiveData<CellState>()
    val currentMove: LiveData<CellState> = mCurrentMove

    init {
        initGame()
    }

    private fun initGame() {
        gameMatrix = Array<CellState>(9) { CellState.None }
        currentCellState = CellState.Cross
        mCurrentMove.value = currentCellState

    }

    fun onReloadClick() {
        initGame()
    }

    fun onCellClick(index: Int) {
        gameMatrix[index] = currentCellState

        currentCellState = if (currentCellState == CellState.Cross) CellState.Zero
        else CellState.Cross
        mCurrentMove.value = currentCellState
    }

    enum class CellState(@DrawableRes val icon: Int, val isClickable: Boolean) {
        None(0, true),
        Cross(R.drawable.ic_cross, false),
        Zero(R.drawable.ic_zero, false)
    }

}
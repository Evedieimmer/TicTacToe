package ru.gorbunova.tictactoe.presentation.main.menu

import androidx.annotation.DrawableRes
import ru.gorbunova.tictactoe.R

enum class CellState(@DrawableRes val icon: Int, val isClickable: Boolean) {
    None(0, true),
    Cross(R.drawable.ic_cross, false),
    Zero(R.drawable.ic_zero, false)
}
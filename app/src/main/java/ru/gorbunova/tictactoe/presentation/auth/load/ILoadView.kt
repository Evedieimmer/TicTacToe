package ru.gorbunova.tictactoe.presentation.auth.load

import com.arellomobile.mvp.MvpView

interface ILoadView : MvpView {
    fun onLoadingComplete()
    fun onLoadingCompleteLogging()
}
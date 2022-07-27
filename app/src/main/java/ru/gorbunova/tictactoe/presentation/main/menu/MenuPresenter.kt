package ru.gorbunova.tictactoe.presentation.main.menu

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import javax.inject.Inject

@InjectViewState
class MenuPresenter : MvpPresenter<IMenuView> {
    @Inject
    constructor()
}
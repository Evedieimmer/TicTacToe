package ru.gorbunova.tictactoe.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.base.ABaseActivity
import ru.gorbunova.tictactoe.presentation.main.game.FragmentGame
import ru.gorbunova.tictactoe.presentation.main.menu.FragmentMenu
import ru.gorbunova.tictactoe.presentation.main.records.FragmentRecordsTable

class GameActivity : ABaseActivity(), INavigateRouterMain {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        if (savedInstanceState != null)
            return
        showMenu()
    }

    override fun showGame() {
        replace(FragmentGame(),"Game")
    }

    override fun showMenu() {
        replace(FragmentMenu())
    }

    override fun showRecords() {
        replace(FragmentRecordsTable(), "Records")
    }
}
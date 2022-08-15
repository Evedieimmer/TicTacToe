package ru.gorbunova.tictactoe.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.gorbunova.tictactoe.App
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.base.ABaseActivity
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.domain.repositories.UserRepository
import ru.gorbunova.tictactoe.presentation.main.game.FragmentGame
import ru.gorbunova.tictactoe.presentation.main.menu.FragmentMenu
import ru.gorbunova.tictactoe.presentation.main.records.FragmentRecordsTable
import javax.inject.Inject

class GameActivity : ABaseActivity(), INavigateRouterMain {

    companion object{
        fun show(){
            App.appContext.let {
                it.startActivity(Intent(it, GameActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
            }
        }
    }

    @Inject
    lateinit var userRepository: UserRepository

    init {
        inject()
    }

    fun inject(){
        DaggerAppComponent.create().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        if (savedInstanceState != null)
            return
        showMenu()
    }

    override fun showGame() {
        replace(FragmentGame(), "Game")
    }

    override fun showMenu() {
        replace(FragmentMenu())
    }

    override fun showRecords() {
        replace(FragmentRecordsTable(), "Records")
    }
}
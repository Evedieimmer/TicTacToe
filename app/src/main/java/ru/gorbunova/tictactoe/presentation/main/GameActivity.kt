package ru.gorbunova.tictactoe.presentation.main

import android.content.Intent
import android.os.Bundle
import ru.gorbunova.tictactoe.App
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.domain.repositories.UserRepository
import ru.gorbunova.tictactoe.presentation.auth.AuthActivity
import ru.gorbunova.tictactoe.presentation.main.game.FragmentGame
import ru.gorbunova.tictactoe.presentation.main.menu.FragmentMenu
import ru.gorbunova.tictactoe.presentation.main.records.FragmentRecordsTable
import soft.eac.appmvptemplate.views.ABaseActivity
import javax.inject.Inject

class GameActivity : ABaseActivity(R.layout.activity_game, R.id.container), INavigateRouterMain {

    companion object {
        fun show() {
            App.appContext.let {
                it.startActivity(Intent(it, GameActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
            }
        }
    }

    @Inject
    lateinit var userRepository: UserRepository
    private val gameFragment: FragmentGame by lazy { FragmentGame() }
    private val menuFragment: FragmentMenu by lazy { FragmentMenu() }
    private val recordFragment: FragmentRecordsTable by lazy { FragmentRecordsTable() }

    init {
        inject()
    }

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null)
            return
        showMenu()
    }

    override fun showGame() {
        replace(gameFragment, "Game")
    }

    override fun showMenu() {
        replace(menuFragment)
    }

    override fun showRecords() {
        replace(recordFragment, "Records")
    }

    override fun goToAuthScreen() {
        AuthActivity.show()
//        val intent = Intent(this, AuthActivity::class.java)
//        startActivity(intent)
    }
}
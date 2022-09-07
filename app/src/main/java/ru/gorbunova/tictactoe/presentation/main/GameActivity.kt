package ru.gorbunova.tictactoe.presentation.main

import android.content.Intent
import android.os.Bundle
import ru.gorbunova.tictactoe.App
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.domain.repositories.UserRepository
import ru.gorbunova.tictactoe.presentation.auth.AuthActivity
import ru.gorbunova.tictactoe.presentation.main.game.FragmentBotGame
import ru.gorbunova.tictactoe.presentation.main.game.FragmentLocalGame
import ru.gorbunova.tictactoe.presentation.main.game.FragmentNetworkGame
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
//    private val gameLocalFragment: FragmentLocalGame by lazy { FragmentLocalGame() }
    private val menuFragment: FragmentMenu by lazy { FragmentMenu() }
    private val recordFragment: FragmentRecordsTable by lazy { FragmentRecordsTable() }

    override fun inject() {
        DaggerAppComponent.create().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null)
            return
        showMenu()
    }

    override fun showLocalGame() {
        replace(FragmentLocalGame.create(), "GameLocal")
    }

    override fun showNetworkGame() {
        replace(FragmentNetworkGame.create(), "NetGame")
    }

    override fun showNetworkGameForBot() {
        replace(FragmentNetworkGame.create(true), "NetGame")
    }

    override fun showBotGame() {
        replace(FragmentLocalGame.create(true), "BotGame")
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
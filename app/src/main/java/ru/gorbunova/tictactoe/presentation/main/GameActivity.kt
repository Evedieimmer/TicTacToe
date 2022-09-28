package ru.gorbunova.tictactoe.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import eac.common.Tools
import ru.gorbunova.tictactoe.App
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.databinding.DialogInputAddressBinding
import ru.gorbunova.tictactoe.domain.di.component.DaggerAppComponent
import ru.gorbunova.tictactoe.domain.repositories.UserRepository
import ru.gorbunova.tictactoe.presentation.auth.AuthActivity
import ru.gorbunova.tictactoe.presentation.main.game.FragmentLocalGame
import ru.gorbunova.tictactoe.presentation.main.game.FragmentNetworkGame
import ru.gorbunova.tictactoe.presentation.main.menu.FragmentMenu
import ru.gorbunova.tictactoe.presentation.main.records.FragmentRecordsTable
import soft.eac.appmvptemplate.views.ABaseActivity
import soft.eac.appmvptemplate.views.ARequestActivity
import javax.inject.Inject

class GameActivity : ARequestActivity(R.layout.activity_game, R.id.container), INavigateRouterMain {

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
        replace(FragmentLocalGame(), "GameLocal")
    }

    override fun showNetworkGame() {
        replace(FragmentNetworkGame(), "NetGame")
    }

    override fun showNetworkGameForBot() {
        replace(FragmentNetworkGame.createWithBot(), "NetGame")
    }

    override fun showBotGame() {
        replace(FragmentLocalGame.createWithBot(), "BotGame")
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

    override fun showHostGame() {
        replace(FragmentLocalGame.createWithServer(8080), "HostGame")
    }

    override fun showGameWithHost() {
        val binding = DialogInputAddressBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .setView(binding.root)
            .setPositiveButton("Подключится") { _, _ -> }
            .setNegativeButton("Выход") { _, _ -> }
            .create().apply { show() }

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val ip = "${binding.etInputIp.text}".trim()
            if (Tools.isIPv4Address(ip)) {
                replace(FragmentNetworkGame.create(ip, 8080), "WithHostGame")
                dialog.dismiss()
            } else
                toast("asdfasdf")
        }
    }
}
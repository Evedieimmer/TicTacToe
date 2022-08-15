package ru.gorbunova.tictactoe.presentation.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import ru.gorbunova.tictactoe.App
import ru.gorbunova.tictactoe.base.ABaseActivity
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.presentation.auth.signIn.FragmentSignIn
import ru.gorbunova.tictactoe.presentation.auth.signUp.FragmentSignUp
import ru.gorbunova.tictactoe.presentation.main.GameActivity

class AuthActivity : ABaseActivity(), INavigateRouter {

    companion object{
        fun show(){
            App.appContext.let {
                it.startActivity(Intent(it, AuthActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        if (savedInstanceState != null)
            return
        showSignIn()
    }

    override fun showSignUp() {
        replace(FragmentSignUp(), "Registration")
    }

    override fun showSignIn() {
        replace(FragmentSignIn())
    }

    override fun goToMenuScreen() {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }
}
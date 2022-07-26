package ru.gorbunova.tictactoe.presentation.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import ru.gorbunova.tictactoe.base.ABaseActivity
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.presentation.auth.signIn.FragmentSignIn
import ru.gorbunova.tictactoe.presentation.auth.signUp.FragmentSignUp
import ru.gorbunova.tictactoe.presentation.main.GameActivity

class AuthActivity : ABaseActivity(), INavigateRouter {

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
}
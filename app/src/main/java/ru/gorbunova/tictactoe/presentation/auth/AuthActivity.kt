package ru.gorbunova.tictactoe.presentation.auth

import android.Manifest
import android.content.Intent
import android.os.Bundle
import ru.gorbunova.tictactoe.App
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.presentation.auth.load.FragmentLoad
import ru.gorbunova.tictactoe.presentation.auth.signIn.FragmentSignIn
import ru.gorbunova.tictactoe.presentation.auth.signUp.FragmentSignUp
import ru.gorbunova.tictactoe.presentation.main.GameActivity
import soft.eac.appmvptemplate.views.ARequestActivity

class AuthActivity : ARequestActivity(R.layout.activity_auth, R.id.container), INavigateRouter {

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
        if (savedInstanceState != null)
            return
         showLoad()
        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            println(it)
        }
    }

    override fun showSignUp() {
        replace(FragmentSignUp(), "Registration")
    }

    override fun showSignIn() {
        replace(FragmentSignIn())
    }

    override fun goToMenuScreen() {
        GameActivity.show()
    }

    override fun showLoad() {
        replace(FragmentLoad())
    }
}
package ru.gorbunova.tictactoe.presentation.auth

import android.content.Intent
import android.os.Bundle
import ru.gorbunova.tictactoe.App
import ru.gorbunova.tictactoe.R
import ru.gorbunova.tictactoe.presentation.auth.load.FragmentLoad
import ru.gorbunova.tictactoe.presentation.auth.signIn.FragmentSignIn
import ru.gorbunova.tictactoe.presentation.auth.signUp.FragmentSignUp
import ru.gorbunova.tictactoe.presentation.main.GameActivity
import soft.eac.appmvptemplate.views.ABaseActivity

class AuthActivity : ABaseActivity(R.layout.activity_auth, R.id.container), INavigateRouter {

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
    }

    override fun showSignUp() {
        replace(FragmentSignUp(), "Registration")
    }

    override fun showSignIn() {
        replace(FragmentSignIn())
    }

    override fun goToMenuScreen() {
        GameActivity.show()
//        val intent = Intent(this, GameActivity::class.java)
//        startActivity(intent)
    }

    override fun showLoad() {
        replace(FragmentLoad())
    }
}
package ru.gorbunova.tictactoe.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.gorbunova.tictactoe.R

abstract class ABaseActivity : AppCompatActivity() {
    fun replace (fragment: Fragment, backStack: String? = null, tag: String? = null){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment,tag).apply {
                backStack?.let{addToBackStack(it)}
            }
            .commit()
    }
}
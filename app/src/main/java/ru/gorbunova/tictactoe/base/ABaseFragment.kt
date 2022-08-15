package ru.gorbunova.tictactoe.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment

abstract class ABaseFragment : MvpAppCompatFragment() {
    init {
        inject()
    }

    abstract fun inject()

    fun toast(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    abstract fun getViewId(): Int
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getViewId(), container, false)
    }
}
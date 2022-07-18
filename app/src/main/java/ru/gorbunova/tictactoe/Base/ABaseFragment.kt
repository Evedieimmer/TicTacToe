package ru.gorbunova.tictactoe.Base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import dagger.android.AndroidInjection.inject

abstract class ABaseFragment : MvpAppCompatFragment() {
init {inject()}

    abstract fun inject()

    fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
    abstract fun getViewId():Int
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getViewId(),container,false)
    }
}
package ru.gorbunova.tictactoe.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.gorbunova.tictactoe.R

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
    }
}
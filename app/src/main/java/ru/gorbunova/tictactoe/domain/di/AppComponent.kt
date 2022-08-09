package ru.gorbunova.tictactoe.di

import dagger.Component
import ru.gorbunova.tictactoe.presentation.auth.signIn.FragmentSignIn
import ru.gorbunova.tictactoe.presentation.auth.signUp.FragmentSignUp
import ru.gorbunova.tictactoe.presentation.main.game.FragmentGame
import ru.gorbunova.tictactoe.presentation.main.menu.FragmentMenu
import ru.gorbunova.tictactoe.presentation.main.records.FragmentRecordsTable

@Component
interface AppComponent {
    fun inject (target: FragmentSignUp)
    fun inject (target: FragmentSignIn)
    fun inject (target: FragmentMenu)
    fun inject (target: FragmentGame)
    fun inject (target: FragmentRecordsTable)
}
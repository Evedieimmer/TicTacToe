package ru.gorbunova.tictactoe.di

import dagger.Component
import ru.gorbunova.tictactoe.presentation.auth.signIn.FragmentSignIn
import ru.gorbunova.tictactoe.presentation.auth.signUp.FragmentSignUp

@Component
interface AppComponent {
    fun inject (target: FragmentSignUp)
    fun inject (target: FragmentSignIn)
}
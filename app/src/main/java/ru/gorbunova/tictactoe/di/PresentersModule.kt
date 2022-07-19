package ru.gorbunova.tictactoe.di

import dagger.Module
import dagger.Provides
import ru.gorbunova.tictactoe.presentation.auth.signIn.SignInPresenter
import ru.gorbunova.tictactoe.presentation.auth.signUp.SignUpPresenter

@Module
class PresentersModule {

    @Provides
    fun provideSignInPresenter(): SignInPresenter {
        return SignInPresenter()
    }

    @Provides
    fun provideSignUpPresenter(): SignUpPresenter{
        return SignUpPresenter()
    }
}
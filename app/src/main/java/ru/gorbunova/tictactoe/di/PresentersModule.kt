package ru.gorbunova.tictactoe.di

import dagger.Module
import dagger.Provides
import ru.gorbunova.tictactoe.presentation.auth.SignInPresenter

@Module
class PresentersModule {

    @Provides
    fun provideSignInPresenter(): SignInPresenter {
        return SignInPresenter()
    }
}
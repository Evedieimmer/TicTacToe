package ru.gorbunova.tictactoe.domain.di

import dagger.Module
import dagger.Provides
import ru.gorbunova.tictactoe.presentation.auth.signIn.SignInPresenter
import ru.gorbunova.tictactoe.presentation.auth.signUp.SignUpPresenter
import ru.gorbunova.tictactoe.presentation.main.game.GamePresenter
import ru.gorbunova.tictactoe.presentation.main.menu.MenuPresenter
import ru.gorbunova.tictactoe.presentation.main.records.RecordsPresenter

//@Module
//class PresentersModule {
//
//    @Provides
//    fun provideSignInPresenter(): SignInPresenter {
//        return SignInPresenter()
//    }
//
//    @Provides
//    fun provideSignUpPresenter(): SignUpPresenter{
//        return SignUpPresenter()
//    }
//
//    @Provides
//    fun provideMenuPresenter(): MenuPresenter{
//        return MenuPresenter()
//    }
//
//    @Provides
//    fun provideGamePresenter(): GamePresenter{
//        return GamePresenter()
//    }
//
//    @Provides
//    fun provideRecordsPresenter(): RecordsPresenter{
//        return RecordsPresenter()
//    }
//}
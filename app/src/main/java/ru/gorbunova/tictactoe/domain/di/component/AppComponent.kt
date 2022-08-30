package ru.gorbunova.tictactoe.domain.di.component


import dagger.Component
import ru.gorbunova.tictactoe.domain.di.module.NetModule
import ru.gorbunova.tictactoe.presentation.auth.load.FragmentLoad
import ru.gorbunova.tictactoe.presentation.auth.signIn.FragmentSignIn
import ru.gorbunova.tictactoe.presentation.auth.signUp.FragmentSignUp
import ru.gorbunova.tictactoe.presentation.main.GameActivity
import ru.gorbunova.tictactoe.presentation.main.game.AFragmentGame
import ru.gorbunova.tictactoe.presentation.main.menu.FragmentMenu
import ru.gorbunova.tictactoe.presentation.main.records.FragmentRecordsTable
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        NetModule::class
    ]
)
interface AppComponent {
    fun inject(target: FragmentSignUp)
    fun inject(target: FragmentSignIn)
    fun inject(target: FragmentMenu)
    fun inject(target: AFragmentGame)
    fun inject(target: FragmentRecordsTable)
    fun inject(target: GameActivity)
    fun inject(target: FragmentLoad)
}
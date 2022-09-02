package ru.gorbunova.tictactoe.presentation.main.game

import io.reactivex.rxjava3.core.Single
import moxy.InjectViewState
import ru.gorbunova.tictactoe.domain.repositories.UserRepository
import ru.gorbunova.tictactoe.domain.repositories.models.rest.Token
import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import ru.gorbunova.tictactoe.gameLogic.IEngine
import ru.gorbunova.tictactoe.gameLogic.ServiceGame
import ru.gorbunova.tictactoe.gameLogic.networkGame.ITokenProvider
import ru.gorbunova.tictactoe.gameLogic.networkGame.NetworkPlayer
import soft.eac.appmvptemplate.common.rx.SubRX
import soft.eac.appmvptemplate.common.rx.standardIO
import soft.eac.appmvptemplate.tools.ABasePresenter
import javax.inject.Inject

@InjectViewState
class NetworkGamePresenter @Inject constructor(
    private var userRepository: UserRepository
) : ABasePresenter<INetworkGameView>(), ITokenProvider {

    /**
     * задача презентера создать игрока и передать информацию о пользователе сюда
     */
    fun createPlayers(engine: IEngine) {
        val user = userRepository.getUser()
        if (user == null) {
            onAuthError(engine, IllegalStateException("User undefined"))
            return
        }
        NetworkPlayer(user).apply {
            engine.addPlayer(this)
//            this.ready() //ТАК НЕЛЬЗЯЯ нужно запросить готовность
        }
    }

    override fun provideToken(): String {
        return userRepository.getToken()?.access ?: ""
    }

    override fun onAuthError(engine: IEngine, e: Exception) {
        ServiceGame.endGame()
        Single.create { emitter ->
            userRepository.getToken()?.also { token ->
                userRepository.refreshToken(token)?.also {
                    emitter.onSuccess(it)
                } ?: emitter.onError(Exception())
            } ?: emitter.onError(Exception())
        }.standardIO(SubRX { token, th ->
            th?.also {
                userRepository.logOut(SubRX { _, _ ->
                    viewState.goToAuth()
                })
            }
            token?.also {
                viewState.goToMenu()
            }
        })

    }
}
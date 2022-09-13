package ru.gorbunova.tictactoe.gameLogic.localServerGame

import eac.network.Connection
import eac.network.PackageReceiver
import eac.network.PackageSender
import ru.gorbunova.tictactoe.gameLogic.networkGame.GameEngineNetwork
import ru.gorbunova.tictactoe.gameLogic.networkGame.ITokenProvider


class ConnectToLocalServer(
    _connection: Connection? = null,
    _tokenProvider: ITokenProvider
) {

    //

    private val connectionListener: (Connection, ByteArray) -> Unit = { _, bytes ->
        val command = GameEngineNetwork.Command(bytes.decodeToString())
        println("COMMAND: $command")
    }

    private val connection = _connection
    private val tokenProvider = _tokenProvider

    private val sender = PackageSender(connection)
    private val receiver = PackageReceiver(connection, connectionListener)

    private fun onAuth(fakePlayer: FakePlayer) {
        //Проверить существует ли такой токен (проверить формат?)
        //Проверить есть ли уже такой подключенный пользователь  = сравнить с фейкплеером

        try {
            val accessToken = tokenProvider.provideToken()
            if(accessToken != null)


                send(GameEngineNetwork.COMMAND_SUCCESS)
        } catch (e: Exception) {
            send(GameEngineNetwork.COMMAND_ERROR, e.localizedMessage)
        }
    }

    private fun send(command: GameEngineNetwork.Command) {
        sender.send("$command")
    }

    private fun send(name: String, data: String? = null) {
        send(GameEngineNetwork.Command(name).apply { this.data = data })
    }

    private fun authorization() {
        send(GameEngineNetwork.COMMAND_AUTHORIZATION)
    }
}
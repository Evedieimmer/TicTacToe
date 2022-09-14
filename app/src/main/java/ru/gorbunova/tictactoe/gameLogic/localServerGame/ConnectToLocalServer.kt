package ru.gorbunova.tictactoe.gameLogic.localServerGame

import eac.network.Connection
import eac.network.PackageReceiver
import eac.network.PackageSender
import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import ru.gorbunova.tictactoe.gameLogic.networkGame.GameEngineNetwork


class ConnectToLocalServer(
    _connection: Connection? = null,
    _fakePlayer: FakePlayer
) {
// данный класс-прослойка занимается регистрацией игрока на сервере: встреча подключения, индентификация
// требуется создание обертки-подключения, для использования Receiver и Sender
// 1) Придет подключение -> "AUTHORIZATION"
// 2) Придет токен -> "SUCCESS" + GAMES:["tic-tac-toe"]" / "ERROR:<error message>"
// 3) Придет "GAME:tic-tac-toe" -> RENDER.. - это уже в fakePlayer


    private val fakePlayer = _fakePlayer
    private val connection = _connection

    private val connectionListener: (Connection, ByteArray) -> Unit = { connection, bytes ->
        if(bytes != null)
            onAuth("$bytes")
        else authorization()
    }

    private val sender = PackageSender(connection)
    private val receiver = PackageReceiver(connection, connectionListener)

    private fun onAuth(sendToken: String) {
        //Проверить существует ли такой токен (проверить формат?)
        //Проверить есть ли уже такой подключенный пользователь  = сравнить с фейкплеером
        try {
            if(sendToken == fakePlayer.provideToken()) {
                //проверка на повторное подключение
            }
            val regex = Regex("""\w{8}-\w{4}-\w{4}-\w{4}-\w{12}""")
            if (regex.matches(sendToken))
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
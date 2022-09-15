package ru.gorbunova.tictactoe.gameLogic.localServerGame

import eac.network.Connection
import eac.network.PackageReceiver
import eac.network.PackageSender
import ru.gorbunova.tictactoe.domain.repositories.models.rest.User
import ru.gorbunova.tictactoe.gameLogic.networkGame.GameEngineNetwork


class Authentication(
    private val connection: Connection,
    private val onSuccess: (Connection) -> Unit
) {
// данный класс-прослойка занимается регистрацией игрока на сервере: встреча подключения, индентификация
// требуется создание обертки-подключения, для использования Receiver и Sender
// 1) Придет подключение -> "AUTHORIZATION"
// 2) Придет токен -> "SUCCESS" + GAMES:["tic-tac-toe"]" / "ERROR:<error message>"
// 3) Придет "GAME:tic-tac-toe" -> RENDER.. - это уже в fakePlayer

    private val sender = PackageSender(connection)

    init {
        PackageReceiver(connection) { _, bytes ->
            onAuth(bytes.decodeToString())
        }

        authorization()
    }

    private fun onAuth(sendToken: String) {
        //Проверить существует ли такой токен (проверить формат?)
        //Проверить есть ли уже такой подключенный пользователь  = сравнить с фейкплеером
        val regex = Regex("""\w{8}-\w{4}-\w{4}-\w{4}-\w{12}""")
        if (regex.matches(sendToken)) {
            send(GameEngineNetwork.COMMAND_SUCCESS)
            onSuccess(connection)
        }
        else
            send(GameEngineNetwork.COMMAND_ERROR, "WRONG TOKEN")
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
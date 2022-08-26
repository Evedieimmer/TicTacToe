package ru.gorbunova.tictactoe.gameLogic.networkGame

import eac.network.*
import ru.gorbunova.tictactoe.gameLogic.AEngine
import ru.gorbunova.tictactoe.gameLogic.IGameState
import ru.gorbunova.tictactoe.gameLogic.IPlayer

class GameEngineNetwork(
    private val ip: String,
    private val port: Int,
    private val tokenProvider: () -> String
):  AEngine(){

    companion object {

        // Команды сервера
        private const val COMMAND_AUTHORIZATION = "AUTHORIZATION"
        private const val COMMAND_SUCCESS = "SUCCESS"
        private const val COMMAND_GAMES = "GAMES"
        private const val COMMAND_RENDER = "RENDER"

        //команды клиента
        private const val COMMAND_GAME = "GAME"
        private const val COMMAND_READY = "READY"
        private const val COMMAND_CELL = "CELL"
        private const val COMMAND_EXIT = "EXIT"
        private const val COMMAND_STATE = "STATE"

    }

    private class Command {

        companion object {
            private const val DIVIDER = ":"
        }

        val name: String
        var data: String? = null

        constructor(string: String) {
            val index = string.indexOf(DIVIDER)
            if (index == -1) {
                name = string
                return
            }

            name = string.substring(index)
            if (index < string.length - 1)
                data = string.substring(index + 1, string.length)
        }

        override fun toString(): String {
            return StringBuilder(name).apply {
                data?.also { append("$DIVIDER$it") }
            }.toString()
        }
    }

    private var renderGame: RemoteState? = null
    private val sender = PackageSender() // отправитель
    private val receiver = PackageReceiver() //приемник
    private var connection: Connection? = null

    private val connectionListener: (Connection, ByteArray) -> Unit = { _, bytes ->
        val command = Command(bytes.decodeToString())
        when (command.name) {
            COMMAND_AUTHORIZATION -> onAuthorization {

            }
            COMMAND_SUCCESS -> {

            }

            else -> {
                //обработать ошибку
            }
        }

    }

    override fun initGame() {
        connection = TcpReconnect(ip, port, 10000L).apply {

            this
                .setOnConnecting<Connection> {

                }
                .setOnConnected<Connection> {

                }
                .setOnDisconnected<Connection> {

                }
                .setOnError<Connection> { connection, throwable ->
                    false
                }

            sender.register(this)
            receiver.register(this, connectionListener)
        }
    }

    override fun addPlayer(player: IPlayer) {
        val game = checkGame()
        player.setEngine(this)

    }

    override fun ready(player: IPlayer) {
        TODO("Not yet implemented")
    }

    override fun executeMove(player: IPlayer, indexCell: Int) {
        TODO("Not yet implemented")
    }

    override fun getState(): IGameState {
        TODO("Not yet implemented")
    }

    override fun getPlayer1(): IPlayer {
        TODO("Not yet implemented")
    }

    override fun getPlayer2(): IPlayer? {
        TODO("Not yet implemented")
    }

    override fun getCurrentPlayer(): IPlayer? {
        TODO("Not yet implemented")
    }

    override fun endGame() {
        TODO("Not yet implemented")
    }

    override fun restart() {
        TODO("Not yet implemented")
    }

    override fun isGameOver(): Boolean {
        TODO("Not yet implemented")
    }

    private fun render() {
        listeners.onEach { it.invoke(this) }
    }

    private fun checkGame() = renderGame ?: throw IllegalStateException("Нет игры")
}
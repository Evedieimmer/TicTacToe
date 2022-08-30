package ru.gorbunova.tictactoe.gameLogic.networkGame

import com.google.gson.Gson
import eac.network.*
import ru.gorbunova.tictactoe.App
import ru.gorbunova.tictactoe.gameLogic.AEngine
import ru.gorbunova.tictactoe.gameLogic.IGameState
import ru.gorbunova.tictactoe.gameLogic.IPlayer
import java.io.IOException

class GameEngineNetwork(
    private val ip: String,
    private val port: Int,
    private val tokenProvider: ITokenProvider
):  AEngine(){

    companion object {

        // Команды сервера
        private const val COMMAND_AUTHORIZATION = "AUTHORIZATION" //запрос на подключение к серверу
        private const val COMMAND_SUCCESS = "SUCCESS" //в случае удачной авторизации на сервере
        private const val COMMAND_ERROR = "ERROR" // в случае неудачной авторизации -> нужно выкинуть пользователя на экран авторизации
        private const val COMMAND_GAMES = "GAMES" //список игр с сервера
        private const val COMMAND_RENDER = "RENDER" // состояние игры


        //команды клиента (в ответе всегда приходит рендер/состояние игры)
        private const val COMMAND_GAME = "GAME" //отправка названия игры
        private const val COMMAND_READY = "READY" //готовность игрока
        private const val COMMAND_CELL = "CELL" //выполение хода
        private const val COMMAND_EXIT = "EXIT" //выход из игры
        private const val COMMAND_STATE = "STATE" //узнать состояние текущей игры

        private const val TYPE_OF_GAME = "tic-tac-toe"

        private val gson = Gson()

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
    private var localPlayer: IPlayer? = null

    // Слушатель для команд от сервера
    private var listenerCallback: ((Command?) -> Unit)? = null
    // Слушатель для асинхронных запросов
    private var initGameCallback: ((Throwable?) -> Unit)? = null

    private val connectionListener: (Connection, ByteArray) -> Unit = { _, bytes ->
        val command = Command(bytes.decodeToString())
        println("$command")

        val callback = listenerCallback
        if (callback != null) {

            listenerCallback = null
            callback(command)

        } else {
            when (command.name) {
                COMMAND_AUTHORIZATION -> onAuthorization()
//                COMMAND_ERROR -> onErrorAuthorization() //пройти заново авторизацию
//                COMMAND_SUCCESS -> { //ждать списка доступных игр, если нет, то вновь подключиться
//
//                }
                COMMAND_GAMES -> onSelectGame()//выбрать игру и отправить ответ GAME:tic-tac-toe
                COMMAND_RENDER -> onRender(gson.fromJson(command.data ?: throw IllegalStateException ("Не пришел рендер"), RemoteState::class.java))
                else -> {
                    //обработать ошибку
                }
            }
        }
    }

    override fun initGame(call: (Throwable?) -> Unit) {
        initGameCallback = call
        connection = TcpReconnect(ip, port, 10000L).apply {

            this
                .setOnConnecting<Connection> {

                }
                .setOnConnected<Connection> {

                }
                .setOnDisconnected<Connection> {

                }
                .setOnError<Connection> { _, throwable ->
                    onInitGameCallback(throwable)
                    false
                }

            sender.register(this)
            receiver.register(this, connectionListener)
        }
    }

    override fun addPlayer(player: IPlayer) {
        localPlayer = player
        player.setEngine(this)
    }

    override fun ready(player: IPlayer) {
        send(COMMAND_READY)
    }

    override fun executeMove(player: IPlayer, indexCell: Int) { //"CELL:[0-8]"


        val cells = checkGame().game
        send("$COMMAND_CELL : ${cells.toString()}")
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
        super.endGame()
        send(COMMAND_EXIT)
        App.handler.postDelayed({
            connection?.also {
                connection = null
                it.shutdown()
            }
        }, 1000)
    }

    override fun restart() {
        TODO("Not yet implemented")
    }

    override fun isGameOver(): Boolean {
        TODO("Not yet implemented")
    }

    private fun checkGame() = renderGame ?: throw IllegalStateException("Нет игры")

    private fun onAuthorization() {

        setListenerCallback { command ->

            if (command == null) {
                tokenProvider.onAuthError( this, IOException("Нет ответа"))
            } else {
                when (command.name) {
                    COMMAND_SUCCESS -> { }
                    COMMAND_ERROR -> {
                        tokenProvider.onAuthError(this, IllegalStateException(command.data ?: "Error"))
                    }
                    else -> throw  IllegalStateException ("")
                }
            }
        }
        send(tokenProvider.provideToken())
    }

    private fun send(command: Command) {
        sender.send("$command")
    }

    private fun send(name: String, data : String? = null) {
        send(Command(name).apply { this.data = data })
    }

    private fun onRender(state: RemoteState) {
        renderGame = state
        //здесь заканчивается initGame!
    }

    private fun onSelectGame() {
        setListenerCallback { command ->
            if(command?.name == COMMAND_RENDER) {
                onInitGameCallback()
            } else {

            }
        }
        send(COMMAND_GAME, TYPE_OF_GAME)
    }

    private fun setListenerCallback(l: (Command?) -> Unit) {
        listenerCallback?.also { throw IllegalStateException("Нет ") }
        listenerCallback = l
    }

    private fun onInitGameCallback(throwable: Throwable? = null) {
        throwable?.printStackTrace()
        initGameCallback?.also {
            initGameCallback = null
            it.invoke(throwable)
        }
    }

//    private fun onListenerCallback(command: Command) {
//        listenerCallback?.also {
//            it.invoke(command)
//        } ?: throw IllegalStateException ("Error")
//    }
}
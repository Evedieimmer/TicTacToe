package ru.gorbunova.tictactoe.gameLogic.networkGame

import com.google.gson.Gson
import eac.network.*
import ru.gorbunova.tictactoe.App
import ru.gorbunova.tictactoe.gameLogic.base.AEngine
import ru.gorbunova.tictactoe.gameLogic.base.IGameState
import ru.gorbunova.tictactoe.gameLogic.base.IPlayer
import ru.gorbunova.tictactoe.gameLogic.base.INetworkPlayer
import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean

class GameEngineNetwork(
    private val ip: String,
    private val port: Int,
    private val tokenProvider: ITokenProvider
) : AEngine() {

    companion object {

        // Команды сервера
        const val COMMAND_AUTHORIZATION = "AUTHORIZATION" //запрос на подключение к серверу
        const val COMMAND_SUCCESS = "SUCCESS" //в случае удачной авторизации на сервере
        const val COMMAND_ERROR = "ERROR" // в случае неудачной авторизации -> нужно выкинуть пользователя на экран авторизации
        const val COMMAND_GAMES = "GAMES" //список игр с сервера
        const val COMMAND_RENDER = "RENDER" // состояние игры

        //команды клиента (в ответе всегда приходит рендер/состояние игры)
        const val COMMAND_GAME = "GAME" //отправка названия игры
        const val COMMAND_READY = "READY" //готовность игрока
        const val COMMAND_CELL = "CELL" //выполение хода
        const val COMMAND_EXIT = "EXIT" //выход из игры
        const val COMMAND_STATE = "STATE" //узнать состояние текущей игры

        const val TYPE_OF_GAME = "tic-tac-toe"

        private val gson = Gson()
    }

     class Command {

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

            name = string.substring(0, index)
            if (index < string.length - 1)
                data = string.substring(index + 1, string.length)
        }

        override fun toString(): String {
            return StringBuilder(name).apply {
                data?.also { append("$DIVIDER$it") }
            }.toString()
        }
    }

    private val isExitGameFlag: AtomicBoolean = AtomicBoolean(false)
    private var remoteState: RemoteState? = null
        set(value) {
            field = value
        }
    private val sender = PackageSender() // отправитель
    private val receiver = PackageReceiver() //приемник
    private var connection: Connection? = null
    private var localPlayer: IPlayer? = null
    private val playersIds = mutableListOf<Int>()

    // Слушатель для команд от сервера
    private var listenerCallback: ((Command?) -> Unit)? = null

    // Слушатель для асинхронных запросов
    private var initGameCallback: ((Throwable?) -> Unit)? = null

    private val connectionListener: (Connection, ByteArray) -> Unit = { _, bytes ->
        val command = Command(bytes.decodeToString())
        println("COMMAND: $command")

        val callback = listenerCallback
        if (callback != null) {

            listenerCallback = null
            callback(command)

        } else {
            when (command.name) {
                COMMAND_AUTHORIZATION -> onAuthorization()
                COMMAND_GAMES -> onSelectGame()//выбрать игру и отправить ответ GAME:tic-tac-toe
                COMMAND_RENDER -> onRender(getRemoveState(command))
                else -> {
                    //обработать ошибку
                }
            }
        }
    }

    private fun getRemoveState(command: Command): RemoteState {
        if (command.name != COMMAND_RENDER)
            throw IllegalArgumentException("")
        return gson.fromJson(
            command.data
                ?: throw IllegalStateException("Не пришел рендер"), RemoteState::class.java
        )
    }

    override fun initGame(call: (Throwable?) -> Unit) {
        initGameCallback = call
        connection = TcpReconnect(ip, port, 10000L).apply {

                setOnConnecting<Connection> {

                }
                setOnConnected<Connection> {

                }
                setOnDisconnected<Connection> {

                }
                setOnError<Connection> { _, throwable ->
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
        render()
    }

    override fun ready(player: IPlayer) {
        send(COMMAND_READY)
    }

    override fun executeMove(player: IPlayer, indexCell: Int) {
        send(COMMAND_CELL, "$indexCell")
    }

    override fun getState(): IGameState = remoteState ?: throw IllegalStateException("Error")

    override fun getPlayer1(): IPlayer? {
        if (playersIds.isEmpty())
            return null

        val localPlayer = this.localPlayer ?: throw IllegalStateException("error")
        return checkState().players.firstOrNull {
            it.getId() == playersIds[0]
        }?.let {
            if (it.getId() == localPlayer.getId()) localPlayer else it
        }
    }

    override fun getPlayer2(): IPlayer? {
        if (playersIds.size < 2)
            return null

        val localPlayer = this.localPlayer ?: throw IllegalStateException("error")
        return checkState().players.firstOrNull {
            it.getId() == playersIds[1]
        }?.let {
            if (it.getId() == localPlayer.getId()) localPlayer else it
        }
    }

    override fun getActionPlayer(): IPlayer? {
        val localPlayer = this.localPlayer ?: throw IllegalStateException("error")
        val remotePlayer = checkState().players.firstOrNull { it.action } ?: return null
        return if (remotePlayer.getId() == localPlayer.getId()) localPlayer else null
    }

    override fun endGame() {
        super.endGame()

        connection?.also {
            connection = null
            receiver.unregister()
            it.setOnSuccess<Connection> { _ ->
                sender.unregister()
                it.shutdown()
            }
            if (isExitGameFlag.get()) {
                App.handler.postDelayed({ it.shutdown() }, 5000)
            }
        }

        send(COMMAND_EXIT)
        isExitGameFlag.set(true)
    }

    override fun restart() {
        (localPlayer as? INetworkPlayer)?.setReady(false)
        localPlayer?.ready()
        render()
    }

    override fun isGameOver(): Boolean = checkState().isGameOver()

    override fun render() {
        App.handler.post {
            super.render()
        }
    }

    override fun getLocalPlayer(): IPlayer? = localPlayer

    private fun checkState() = remoteState ?: throw IllegalStateException("Нет игры")

//    private fun getPlayerReady(): Boolean {
//        val currentUser = getCurrentPlayer()
//        val remotePlayer = checkState().players.firstOrNull { it.getId() == currentUser?.getId() }
//        return remotePlayer?.isReady() ?: false
//    }

    private fun onAuthorization() {

        setListenerCallback { command ->

            if (command == null) {
                tokenProvider.onAuthError(this, IOException("Нет ответа"))
            } else {
                when (command.name) {
                    COMMAND_SUCCESS -> {}
                    COMMAND_ERROR -> {
                        tokenProvider.onAuthError(
                            this,
                            IllegalStateException(command.data ?: "Error")
                        )
                    }
                    else -> throw  IllegalStateException("Wrong: ${command.name}")
                }
            }
        }
        send(tokenProvider.provideToken())
    }

    private fun send(command: Command) {
        sender.send("$command".also { println("$it") })
    }

    private fun send(name: String, data: String? = null) {
        send(Command(name).apply { this.data = data })
    }

    private fun onRender(state: RemoteState) {
        remoteState = state
        onInitGameCallback()
        updatePlayerIds(state)
        getLocalPlayer(state)?.also {
            (localPlayer as? INetworkPlayer)?.setReady(it.isReady())
        }
        render()
    }

    private fun updatePlayerIds(state: RemoteState) {
        val players = state.players
        when (playersIds.size) {
            0 -> {
                when (players.size) {
                    1 -> {
                        playersIds.add(players[0].getId())
                    }
                    2 -> {
                        playersIds.add(players[0].getId())
                        playersIds.add(players[1].getId())
                    }
                }
            }
            1 -> when (players.size) {
                1 -> {
                    //это придет тот же игрок
                }
                2 -> {
                    if (playersIds[0] == players[0].getId())
                        playersIds.add(players[1].getId())
                    else {
                        playersIds.clear()
                        updatePlayerIds(state)
                    }
                }
            }
            2 -> if (players.size > 1 && playersIds[0] != players[0].getId()) {
                playersIds.clear()
                updatePlayerIds(state)
            }
        }
    }

    private fun getLocalPlayer(state: RemoteState): IPlayer? {
        val localPlayerId = this.localPlayer?.getId() ?: throw IllegalStateException("error")
        return state.players.firstOrNull { it.getId() == localPlayerId }
    }

    private fun onSelectGame() {
//        setListenerCallback { command ->
//            if (command?.name == COMMAND_RENDER) {
//                remoteState = getRemoveState(command)
//                onInitGameCallback()  //здесь заканчивается initGame!
//            } else {
//                // todo
//            }
//        }
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
package ru.gorbunova.tictactoe.gameLogic.localServerGame

import eac.common.Tools
import eac.network.Server
import ru.gorbunova.tictactoe.App
import ru.gorbunova.tictactoe.gameLogic.base.AEngine
import ru.gorbunova.tictactoe.gameLogic.base.IGameState
import ru.gorbunova.tictactoe.gameLogic.base.INetworkPlayer
import ru.gorbunova.tictactoe.gameLogic.base.IPlayer
import kotlin.random.Random

class GameEngineLocalServer(
    private val port: Int = 0
) : AEngine() {

    companion object {

        private val random: Random by lazy { Random(System.currentTimeMillis()) }

        fun getActionType(actionType: Int = IGameState.GAME_CELL_VALUE_NONE) = when (actionType) {
            IGameState.GAME_CELL_VALUE_CROSS -> IGameState.GAME_CELL_VALUE_ZERO
            IGameState.GAME_CELL_VALUE_ZERO -> IGameState.GAME_CELL_VALUE_CROSS
            else -> random.nextInt(IGameState.GAME_CELL_VALUE_CROSS + 1)
        }
    }

    private class GameState(

        private var cells: IntArray = IntArray(9) { -1 },
        internal var winner: IPlayer? = null,
        var statusGame: Int = IGameState.STATE_WAITING_PLAYERS_READY

    ) : IGameState {

        var player1: IPlayer? = null
        var player2: IPlayer? = null
        var currentPlayer: IPlayer? = null

        fun executeMove(index: Int, value: Int) {
            if (index < 0 || index > 8) throw IllegalArgumentException("массив от 0 до 8")
            when (value) {
                IGameState.GAME_CELL_VALUE_ZERO,
                IGameState.GAME_CELL_VALUE_CROSS -> {
                }
                else -> throw IllegalArgumentException("значение может быть только 1 или 0")
            }
            if (cells[index] != IGameState.GAME_CELL_VALUE_NONE) throw IllegalStateException("не может игрок передавать значение -1")
            cells[index] = value
        }

        override fun getWinner() = winner

        override fun setStatus(value: Int) {
            statusGame = value
        }

        override fun restart() {
            cells = IntArray(9) { -1 }
            winner = null
//            statusGame = IGameState.STATE_WAITING_PLAYERS_READY
        }

        override fun getStatus(): Int = statusGame

        override fun getCells() = cells.copyOf()

        override fun isGameOver() = winner != null || !cells.any { it == IGameState.GAME_CELL_VALUE_NONE }

        fun changeTurn() {
            currentPlayer = if (currentPlayer == player1) player2 else player1
        }

        fun isWin(player: IPlayer): Boolean {
            val userTurnType: Int = player.getActionType()
            return checkLines(userTurnType) || checkColumns(userTurnType) || checkCrosses(
                userTurnType
            )
        }

        private fun checkLines(userTurnType: Int): Boolean {
            return line(userTurnType, 0) || line(userTurnType, 1) || line(userTurnType, 2)
        }

        private fun checkColumns(userTurnType: Int): Boolean {
            return column(userTurnType, 0) || column(userTurnType, 1) || column(userTurnType, 2)
        }

        private fun checkCrosses(userTurnType: Int): Boolean {
            val game: IntArray = cells
            return if (game[4] != userTurnType)
                false else game[0] == userTurnType && game[8] == userTurnType || game[2] == userTurnType && game[6] == userTurnType
        }

        private fun line(userTurnType: Int, bias: Int): Boolean {
            val shift = 3 * bias
            val game: IntArray = cells
            return game[shift] == userTurnType && game[shift + 1] == userTurnType && game[shift + 2] == userTurnType
        }

        private fun column(userTurnType: Int, bias: Int): Boolean {
            val game: IntArray = cells
            return game[bias] == userTurnType && game[bias + 3] == userTurnType && game[bias + 6] == userTurnType
        }

        fun setRandomPlayer() {
            currentPlayer = if (random.nextBoolean()) player1 else player2
        }
    }

    private var player1ready: IPlayer? = null
    private var player2ready: IPlayer? = null

    // переменные для сетевой игры
    private var server: Server? = null

    fun getIp() = Tools.getInetAddress()?.hostAddress ?: "127.0.0.1"
    fun getPort() = port

    override fun initGame(call: (Throwable?) -> Unit) {
        gameState = GameState()
        // Если надо, поднимаем сервер
        if (port > 0) createServer(port, call)
        else call(null)
    }

    override fun addPlayer(player: IPlayer) {

        val state = checkState()
        player.setEngine(this)

        if (state.player1 == null) {
            player.setActionType(getActionType())
            state.player1 = player
            // если серверная игра, то добавить фейк плеера
            return
        }
        if (state.player2 == null) {
            player.setActionType(getActionType(state.player1!!.getActionType()))
            state.player2 = player
            return
        }
        throw IllegalStateException("Не более 2х игроков")
        render() //первый рендер с одним игроком, статус - ожидание
    }

    override fun ready(player: IPlayer) {

        val player1 = player1ready

        if (player1ready == null)
            player1ready = player
        else if (player1 != player && player2ready == null) {
            player2ready = player
            // start game
            checkState().also {
                it.setRandomPlayer()
                it.setStatus(IGameState.STATE_GAME_PROCESSING)
            }
        }
        render() // рендер с двумя игроками, и статусом - в процессе
    }

    override fun executeMove(player: IPlayer, indexCell: Int) {

        val gameState = checkState()
        gameState.winner?.also { throw IllegalStateException("Победитель найден") }
        gameState.player2 ?: throw IllegalStateException("Нет игрока")

        gameState.executeMove(indexCell, player.getActionType())

        checkWinner(gameState)?.also {
            gameState.winner = it
            it.onWin()
            render()
            return
        }
        gameState.changeTurn()
        render()
    }

    override fun getState(): IGameState = checkState()

    override fun getPlayer1(): IPlayer =
        checkState().player1 ?: throw IllegalStateException("Нет 1го игрока")

    override fun getPlayer2(): IPlayer? = checkState().player2

    override fun getActionPlayer() = checkState().currentPlayer

    override fun getLocalPlayer(): IPlayer? = null

    override fun endGame() {
        super.endGame()
        //если серверная игра - остановить сервер
        server?.also {

        }
        gameState = null
        player1ready = null
        player2ready = null
    }

    override fun render() {
        (gameState as? GameState)?.also { state ->
            (state.player1 as? INetworkPlayer)?.also {
                if (player1ready != null)
                    it.setReady(true)
            }
            (state.player2 as? INetworkPlayer)?.also {
                if (player2ready != null)
                    it.setReady(true)
            }
        }
        super.render()
    }

    override fun restart() {
        val state = checkState().also { it.restart() }
        val player1 = state.player1 ?: throw IllegalStateException("Нет состояния для рестарта")
        val player2 = state.player2 ?: throw IllegalStateException("Нет состояния для рестарта")

        player1ready = null
        player2ready = null

        player1.setActionType(getActionType())
        player2.setActionType(getActionType(player1.getActionType()))
        render()
    }

    private fun checkState() = gameState as? GameState ?: throw IllegalStateException("Нет состояния игры")

    private fun checkWinner(state: GameState): IPlayer? {

        state.player1?.also {
            if (state.isWin(it))
                return it
        }

        state.player2?.also {
            if (state.isWin(it))
                return it
        }

        return null
    }

    private fun createServer(port: Int, call: (Throwable?) -> Unit) {
        server?.shutdown()
        server = Server(port, Server.Protocol.TCP)
            .setOnStart {
                App.handler.post { call(null) }
            }
            .setOnConnected {

            }
            .setOnDisconnected {

            }
            .setOnError { server, throwable ->
                throwable.printStackTrace()
                false
            }
            .setOnStop {
                // если сервер не стартанул - кинуть ошибку и обработать ее уровнем выше
            }
    }
}
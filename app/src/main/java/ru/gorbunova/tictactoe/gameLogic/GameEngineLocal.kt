package ru.gorbunova.tictactoe.gameLogic

import androidx.camera.core.UseCaseGroup
import ru.gorbunova.tictactoe.gameLogic.IGameState.Companion.STATE_GAME_END
import ru.gorbunova.tictactoe.gameLogic.IGameState.Companion.STATE_WAITING_PLAYERS_READY
import kotlin.random.Random

class GameEngineLocal() : IEngine {

    companion object {

        private val random: Random by lazy { Random(System.currentTimeMillis()) }

        fun getActionType(actionType: Int = IGameState.GAME_CELL_VALUE_NONE) = when(actionType) {
            IGameState.GAME_CELL_VALUE_CROSS -> IGameState.GAME_CELL_VALUE_ZERO
            IGameState.GAME_CELL_VALUE_ZERO -> IGameState.GAME_CELL_VALUE_CROSS
            else -> random.nextInt(IGameState.GAME_CELL_VALUE_CROSS + 1)
        }
    }

    class GameState(

        private val cells: IntArray = IntArray(9) { -1 },
        internal var winner: IPlayer? = null,
        //какой ход ожидается
        private var currentActionType: Int = getActionType(),
        var statusGame: Int = STATE_WAITING_PLAYERS_READY

    ) : IGameState {

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

        override fun getWinner () = winner

        override fun setStatus(value: Int) {
            statusGame = value
        }

        override fun getStatus(): Int = statusGame

        fun isGameOver() = winner != null && cells.firstOrNull { it == IGameState.GAME_CELL_VALUE_NONE } == null

        override fun getCells() = cells.copyOf()

        fun changeTurn() {
            currentActionType = getActionType(currentActionType)
        }

        fun isWin(player: IPlayer): Boolean {
            val userTurnType: Int = player.getActionType()
            return checkLines(userTurnType) || checkColumns(userTurnType) || checkCrosses(userTurnType)
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
    }

    private val listeners = mutableListOf<(IEngine) -> Unit>()
    private var gameState: GameState? = null
    private var player1: IPlayer? = null
    private var player2: IPlayer? = null
    private var player1ready: IPlayer? = null
    private var player2ready: IPlayer? = null


    override fun addListener(l: (engine: IEngine) -> Unit) {
        listeners.add(l)
    }

    override fun initGame() {
        gameState = GameState()
    }

    override fun addPlayer(player: IPlayer) {

        player.setEngine(this)

        if (player1 == null) {
            player.setActionType(getActionType())
            player1 = player
            return
        }
        if (player2 == null) {
            player.setActionType(getActionType(player1!!.getActionType()))
            player2 = player
            return
        }
        throw IllegalStateException("Не более 2х игроков")
    }

    override fun ready(player: IPlayer) {

        //если оба игрока здесь, то начнем игру
        val player1 = player1ready

        if (player1ready == null)
            player1ready = player

        else if (player1 != player && player2ready == null) {
            player1ready = player
            // start game
            gameState?.setStatus(IGameState.STATE_GAME_PROCESSING)

        }

        render()
    }

    override fun executeMove(player: IPlayer, indexCell: Int) {

        val gameState = this.gameState ?: throw IllegalStateException("Игра не началась")
        gameState.winner?.also { throw IllegalStateException("Победитель найден") }
        player2 ?: throw IllegalStateException("Нет игрока")

        gameState.executeMove(indexCell, player.getActionType())
        checkWinner(gameState)?.also {
            gameState.winner = it
            it.onWin()
            gameState.setStatus(STATE_GAME_END)
            return
        }

        gameState.changeTurn()
        render()
    }

    private fun checkWinner(state: GameState): IPlayer? {

        player1?.also {
            if (state.isWin(it))
                state.setStatus(STATE_GAME_END)
                return it
        }

        player2?.also {
            if (state.isWin(it))
                state.setStatus(STATE_GAME_END)
                return it
        }

        return null
    }

    private fun render() {
        listeners.onEach { it.invoke(this) }
    }
}
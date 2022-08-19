package ru.gorbunova.tictactoe.gameLogic

class GameEngineLocal() : IEngine {
    class GameState(

        private val cells: IntArray = IntArray(9) { -1 },
        internal var winner: IPlayer? = null

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

        fun getWinner() {

        }

        override fun getCells() = cells.copyOf()

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

    private val listeners = mutableListOf<(IGameState) -> Unit>()
    private var gameState: GameState? = null
    private var player1: IPlayer? = null
    private var player2: IPlayer? = null


    override fun addListener(l: (IGameState) -> Unit) {
        listeners.add(l)
    }

    override fun initGame() {

        gameState = GameState()
    }

    override fun addPlayer(player: IPlayer) {
        if (player1 == null) {
            player1 = player
            return
        }
        if (player2 == null) {
            player2 = player
            return
        }
        throw IllegalStateException("Не более 2х игроков")
    }

    override fun ready(player: IPlayer) {

    }

    override fun executeMove(player: IPlayer, index: Int) {

        val gameState = this.gameState ?: throw IllegalStateException("Игра не началась")
        gameState.winner?.also { throw IllegalStateException("111") }
        player2 ?: throw IllegalStateException("Нет игрока")

        gameState.executeMove(index, player.getActionType())
        gameState.isWin(player)

        val winner: IPlayer = gameState.getWinner()

        checkWinner(gameState)?.also {
            gameState.winner = it
            return
        }
        listeners.onEach { it.invoke(gameState) }
    }

    private fun checkWinner(state: GameState): IPlayer? {

        return null
    }
}
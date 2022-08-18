package ru.gorbunova.tictactoe.gameLogic

class GameEngineLocal() : IEngine {
    class GameState(

        private val cells: IntArray = IntArray(9) {-1},
        internal var winner: IPlayer? = null

    ): IGameState {

        fun executeMove(index: Int, value: Int) {
            if (index < 0 || index > 8) throw IllegalArgumentException("0-8")
            when(value) {
                IGameState.GAME_CELL_VALUE_ZERO,
                IGameState.GAME_CELL_VALUE_CROSS -> {}
                else -> throw IllegalArgumentException("1 0")
            }
            if (cells[index] != IGameState.GAME_CELL_VALUE_NONE) throw IllegalStateException("///")
            cells[index] = value
        }

        override fun getCells() = cells.copyOf()

        fun isWin(player: IPlayer): Boolean {
//доделай
            return false
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
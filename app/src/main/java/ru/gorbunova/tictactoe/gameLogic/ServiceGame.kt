package ru.gorbunova.tictactoe.gameLogic

import ru.gorbunova.tictactoe.gameLogic.localGame.GameEngineLocal

object ServiceGame{

    //сервис не может создать игрока (кроме бота)
    //сервис нужен для хранения объектов на уровне приложения (вдруг юзер решит свернуть приложение)

    const val LOCAL_GAME = 1
    const val GAME_WITH_BOT = 2
    const val NETWORK_GAME = 3

    internal var engine: IEngine? = null
        set(value) {
            field = value
        }

    fun endGame() {
        val engine = this.engine ?: return
        engine.endGame()
        this.engine = null
    }

    fun restartGame() {
        val engine = this.engine ?: return
        engine.restart()
    }

    fun createLocalGame() = GameEngineLocal().apply {
        engine = this
    }

}
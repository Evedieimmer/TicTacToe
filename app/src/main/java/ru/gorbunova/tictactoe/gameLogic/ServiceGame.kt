package ru.gorbunova.tictactoe.gameLogic

import ru.gorbunova.tictactoe.gameLogic.localGame.GameEngineLocal

object ServiceGame{

    //сервис не может создать игрока (кроме бота)
    //сервис нужен для хранения объектов на уровне приложения (вдруг юзер решит свернуть приложение)

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
        engine?.restart()
    }

    fun createLocalGame() = GameEngineLocal().apply {
        engine = this
    }

}
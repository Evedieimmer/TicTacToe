package ru.gorbunova.tictactoe.gameLogic

object ServiceGame{

    //сервис не может создать игрока (кроме бота)
    //сервис нужен для хранения объектов на уровне приложения (вдруг юзер решит свернуть приложение)


    const val LOCAL_GAME = 1
    const val GAME_WITH_BOT = 2
    const val NETWORK_GAME = 3

    //проверка на выбор игры и ее создание
    // локальная - экземпляры локальных классов игроков и движка
    // бот - экземляры локального движка, игрока и бота
    // сетевая - экземпляры двух? сетевых игроков

    // присвоение имени игрока:
    // если локальная то имена по умолчанию
    // если с ботом - имя юзера и "бот"
    // если сетевая - имена юзеров

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
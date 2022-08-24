package ru.gorbunova.tictactoe.gameLogic

object ServiceGame {

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

    internal var winner = ""
    internal var winnerScore = 0
    internal var player1: IPlayer? = null //опционал - значит может быть null
    internal var player2: IPlayer? = null
    internal var engine: IEngine? = null



    fun typeOfGame(typeGame: Int) {
        when(typeGame) {
            LOCAL_GAME -> createLocalGame()
            GAME_WITH_BOT -> createGameWithBot()
            NETWORK_GAME -> createNetworkGame()
            else -> throw IllegalStateException("Игра не выбрана")
        }
    }

    private fun getNamePlayer(): String {
        //забираем имя пользователя
        return ""
    }

     private fun createLocalGame() {

         //создаем gameState
         engine?.apply { initGame() }

//        val gameEngineLocal = GameEngineLocal().apply { initGame() }
//        val player1 = LocalPlayer()
//        val player2 = LocalPlayer()

        //добавляем пользователей в игру и назначем роли: крестик/нолик
         player1?.let { engine?.addPlayer(it) }
         player2?.let { engine?.addPlayer(it) }

        //игроки передают свою готовность движку
        //если все готовы состояние игры получает статус "игра началась"
         (player1)?.ready()
         (player2)?.ready()
    }

    private fun createGameWithBot() {

    }

    private fun createNetworkGame() {

    }

}
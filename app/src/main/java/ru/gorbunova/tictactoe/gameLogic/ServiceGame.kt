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

    private var winner = ""
    private var winnerScore = 0
    private var player1: IPlayer? = null //опционал - значит может быть null
    private var player2: IPlayer? = null
    private var engine: IEngine? = null



    private  fun typeOfGame(typeGame: Int) {
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

        val gameEngineLocal = GameEngineLocal().apply { initGame() }

        val player1 = LocalPlayer()
        val player2 = LocalPlayer()

        gameEngineLocal.addPlayer(player1)
        gameEngineLocal.addPlayer(player2)

        player1.ready()
        player2.ready()





//        gameEngineLocal.addListener { gameEngine ->
//
//        }
    }

    private fun createGameWithBot() {

    }

    private fun createNetworkGame() {

    }

}
package ru.gorbunova.tictactoe.domain.repositories.models.rest.service

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*
import ru.gorbunova.tictactoe.domain.repositories.models.rest.*


interface IUserRestApiService {

    //Авторизация существующего пользователя
    @POST("/user/v1/login") //абсолютный путь
    fun login(@Body user: User): Observable<User>

    //Регистрация нового пользователя
    @PUT("user/v1/registration") //относительный путь
    fun registration(@Body user: User): Observable<User>

    //Обновление токена
    @POST("user/v1/refresh")
    @Headers("Content-Type: application/json")
    fun refreshToken(
        @Header("refresh_token") refreshToken: String
    ): Call<Token>

    //Разавторизовать текущего пользователя
    @DELETE("user/v1/logout")
    fun logOut(@Header("access_token") accessToken: String): Call<Response>

    //Обновление данных пользователя: установка аватара и смена пароля
    @POST("user/v1/update")
    fun updateUser(
        @Header("access_token") accessToken: String,
        @Body userUpdate: UserUpdate
    ): Observable<UserUpdate>

    //Загрузить файл для аватара пользователя (не более 256кб)


    //Сохранить результат игры, если он лучше предыдущего
    @POST("/game/v1/result")
    fun saveGameResult(
        @Header("access_token") accessToken: String,
        @Body gameScore: GameScore
    ): Call<Response>

    //Получить таблицу рекордов
    @GET("/game/v1/results/{game_tag}")
    fun getGameResultTable(
        @Header("access_token") accessToken: String,
        @Path("game_tag") gameTag: Int
    ): Observable<List<GameResult>>
}
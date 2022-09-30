package ru.gorbunova.tictactoe.domain.repositories.models.rest.service

import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
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
    fun logOut(@Header("access_token") accessToken: String): Observable<Response>

    //Обновление данных пользователя: установка аватара и смена пароля
    @POST("user/v1/update")
    fun updateUserAvatar(
        @Header ("access_token") accessToken: String,
        @Body userUpdate: UserUpdate
    ): Observable<UserUpdate>

    //Загрузить файл для аватара пользователя (не более 256кб)
    @Multipart
    @POST("/upload/v1/avatar")
    fun uploadAvatar(
        @Header ("access_token") accessToken: String,
        @Part avatar: MultipartBody.Part
    ): Observable<UploadedFile>

    //Сохранить результат игры, если он лучше предыдущего
    @POST("/game/v1/result")
    fun saveGameResult(
        @Body gameScore: GameScore
    ): Call<Response>

    //Получить таблицу рекордов
    @GET("/game/v1/results/{game_tag}")
    fun getGameResultTable(
        @Path("game_tag") gameTag: Int
    ): Observable<List<GameResult>>
}
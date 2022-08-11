package ru.gorbunova.tictactoe.domain.repositories.models.rest.service

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import ru.gorbunova.tictactoe.domain.repositories.models.rest.User


interface IAuthRestApiService {

    @POST("/user/v1/login")
    fun login(@Body user: User): Observable<User>

    @PUT("user/v1/registration")
    fun registration(@Body user: User): Observable<User>

//    @POST("user/v1/refresh")
//    @Headers("Content-Type: application/json")
//    fun refreshToken(
//        @Header("refresh_token") refreshToken: String
//    ): Call<Token>


}
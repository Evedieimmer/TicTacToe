package ru.gorbunova.tictactoe.domain.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.gorbunova.tictactoe.domain.repositories.UserRepository
import ru.gorbunova.tictactoe.domain.repositories.models.rest.RestClient
import ru.gorbunova.tictactoe.domain.repositories.models.rest.TokenInterceptor
import soft.eac.appmvptemplate.common.net.IRestClient
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
class NetModule {
    companion object {

//        private const val DOMAIN = "212.75.210.227"
        const val DOMAIN = "10.102.100.91"
//        const val DOMAIN = "192.168.0.10"
        const val GAME_SERVICE_PORT = 3456

        const val DOMAIN_MAIN_API = "http://$DOMAIN:8080"
        private const val CONNECTION_TIMEOUT = 20000L

        const val NAME_AUTH_REST_CLIENT = "NAME_AUTH_REST_CLIENT"
        const val NAME_CLIENT_WITHOUT_TOKEN_INTERCEPTOR = "NAME_CLIENT_WITHOUT_TOKEN_INTERCEPTOR"
    }
    //Компонент работы с сетью, хранит в себе роуты и зависимости поставищик на основе окхттп который будет ходить в сеть

    @Provides
    @Singleton
    fun provideTokenInterceptor(userRepository: UserRepository) = TokenInterceptor(userRepository)


    @Provides
    @Singleton
    fun provideLoggerInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    } as Interceptor


    @Provides
    @Singleton
    @Named(NAME_CLIENT_WITHOUT_TOKEN_INTERCEPTOR)
    fun provideOkHttpClientWithOutTokenInterceptor(logger: Interceptor) =
        OkHttpClient.Builder().apply {
            connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            readTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            writeTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            addInterceptor(logger)
        }.build()

    //перехватчик логов и сущность, подставляющая в запросы токены авторизации
    @Provides
    @Singleton
    fun provideOkHttpClientWithTokenInterceptor(
        logger: Interceptor,
        token: TokenInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .addInterceptor(logger)
            .addInterceptor(token)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder().create()


    @Provides
    @Singleton
    @Named(NAME_AUTH_REST_CLIENT)
    fun provideAuthRestClient(
        @Named(NAME_CLIENT_WITHOUT_TOKEN_INTERCEPTOR) client: OkHttpClient,
        gson: Gson
    ) = RestClient(client, gson, DOMAIN_MAIN_API) as IRestClient


    @Provides
    @Singleton
    fun provideMainRestClient(client: OkHttpClient, gson: Gson) =
        RestClient(client, gson, DOMAIN_MAIN_API) as IRestClient
}
package ru.gorbunova.tictactoe.domain.repositories.models.rest

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import soft.eac.appmvptemplate.common.net.IRestClient

class RestClient(private val client: OkHttpClient, gson: Gson, baseUrl: String) : IRestClient {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(client)
        .build()


    override fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }

    override fun cancelAllRequests() {
        client.dispatcher.cancelAll()
    }

}
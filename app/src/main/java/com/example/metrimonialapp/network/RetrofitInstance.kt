package com.example.metrimonialapp.network

import com.example.metrimonialapp.data.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val client = OkHttpClient.Builder()
        .addInterceptor(FlakyNetworkInterceptor())
        .retryOnConnectionFailure(true)  // allow OkHttp to retry low-level failures
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://randomuser.me/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
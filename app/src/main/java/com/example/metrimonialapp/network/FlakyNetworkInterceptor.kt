package com.example.metrimonialapp.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class FlakyNetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (kotlin.random.Random.nextFloat() < 0.3f) {
            throw IOException("Simulated network glitch")
        }
        return chain.proceed(chain.request())
    }
}

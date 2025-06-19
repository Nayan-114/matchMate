package com.example.metrimonialapp.data

import retrofit2.Response
import retrofit2.http.GET

data class UserResponse(val results: List<User>)

interface ApiService {
    @GET("?results=10")
    suspend fun fetchUsers(): Response<UserResponse>
}

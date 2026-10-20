package com.example.cst438_p2_android_app.network
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("api/v1/users/me")
    suspend fun getCurrentUser(
        @Header("Authorization") authorization: String
    ): UserResponse
}
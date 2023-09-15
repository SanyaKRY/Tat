package com.example.tat.features.logout.data.datasource.api.retrofit
import retrofit2.http.POST

interface LogoutApiService {

    @POST("api/logout")
    suspend fun logout(
    )
}
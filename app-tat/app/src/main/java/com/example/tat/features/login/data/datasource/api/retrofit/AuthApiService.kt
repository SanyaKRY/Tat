package com.example.tat.features.login.data.datasource.api.retrofit

import com.example.tat.features.login.data.datasource.api.model.LoginResponseApi
import com.example.tat.features.login.data.datasource.api.model.UserAuthApi
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("api/auth/login")
    suspend fun login(
        @Body userAuthApi: UserAuthApi
    ): LoginResponseApi
}
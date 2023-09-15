package com.example.tat.features.token.api

import com.example.tat.features.token.model.RefreshTokenApi
import com.example.tat.features.token.model.TokensApi
import retrofit2.http.Body
import retrofit2.http.POST

    interface TokenApiService {

    @POST("api/auth/token")
    suspend fun refreshTokens(
        @Body refreshTokenApi: RefreshTokenApi
    ): TokensApi
}
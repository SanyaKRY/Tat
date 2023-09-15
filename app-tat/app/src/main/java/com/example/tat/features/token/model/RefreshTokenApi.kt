package com.example.tat.features.token.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RefreshTokenApi(
    @Json(name = "refreshToken") val refreshToken: String
)
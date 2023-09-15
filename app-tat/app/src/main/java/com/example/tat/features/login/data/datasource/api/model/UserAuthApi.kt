package com.example.tat.features.login.data.datasource.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserAuthApi(
    @Json(name = "username") val username: String,
    @Json(name = "password") val password: String
)

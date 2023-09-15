package com.example.tat.features.home.data.datasource.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserApi(
    @Json(name = "id") val id: Int,
    @Json(name = "username") val username: String,
    @Json(name = "email") val email: String,
    @Json(name = "fullName") val fullName: String,
    @Json(name = "isActive") val isActive: Boolean,
    @Json(name = "role") val role: String,
)

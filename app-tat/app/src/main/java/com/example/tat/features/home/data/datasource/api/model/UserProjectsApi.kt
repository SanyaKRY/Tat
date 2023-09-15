package com.example.tat.features.home.data.datasource.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserProjectsApi(
    @Json(name = "content") val projects: List<ProjectApi>
)

@JsonClass(generateAdapter = true)
data class ProjectApi(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String
)

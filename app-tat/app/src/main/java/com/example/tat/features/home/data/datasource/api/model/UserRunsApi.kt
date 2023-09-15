package com.example.tat.features.home.data.datasource.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserRunsApi(
    @Json(name = "content") val runs: List<RunApi>
)

@JsonClass(generateAdapter = true)
data class RunApi(
    @Json(name = "runId") val runId: Int,
    @Json(name = "entity") val entity: Entity,
    @Json(name = "status") val status: String
)

@JsonClass(generateAdapter = true)
data class Entity(
    @Json(name = "projectName") val projectName: String,
    @Json(name = "name") val entityName: String,
    @Json(name = "id") val entityId: Int
)
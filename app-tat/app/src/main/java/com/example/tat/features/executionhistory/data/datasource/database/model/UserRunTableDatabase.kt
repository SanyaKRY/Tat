package com.example.tat.features.executionhistory.data.datasource.database.model

data class UserRunTableDatabase(
    val runId: Int,
    val projectName: String,
    val entityName: String,
    val entityId: Int,
    val status: String
)

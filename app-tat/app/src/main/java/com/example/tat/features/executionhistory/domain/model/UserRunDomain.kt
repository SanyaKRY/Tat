package com.example.tat.features.executionhistory.domain.model

data class UserRunDomain(
    val runId: Int,
    val projectName: String,
    val entityName: String,
    val entityId: Int,
    val status: String
)

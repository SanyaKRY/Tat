package com.example.tat.features.executionhistory.presentation.model

data class UserRunUi(
    val runId: Int,
    val projectName: String,
    val entityName: String,
    val entityId: Int,
    val status: String,
    val statusColor: StatusColorType
)

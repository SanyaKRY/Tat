package com.example.tat.features.executionhistory.presentation.model

import java.lang.Exception

data class HistoryState(
    val userRunsUi: UserRunsUi = UserRunsUi(emptyList()),
    val isLoading: Boolean = false,
    val error: Exception? = null
)

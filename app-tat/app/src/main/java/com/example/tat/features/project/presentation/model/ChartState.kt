package com.example.tat.features.project.presentation.model

import com.example.tat.features.project.domain.model.Chart
import java.lang.Exception

data class ChartState(
    val chart: Chart? = null,
    val isLoading: Boolean = false,
    val isVisableChart: Boolean = false,
//    val error: Exception? = null
)

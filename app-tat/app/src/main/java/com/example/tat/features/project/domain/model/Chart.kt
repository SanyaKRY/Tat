package com.example.tat.features.project.domain.model

import com.example.tat.features.project.presentation.model.SuiteOfProject

data class Chart (
    val pass: Int,
    val failed: Int,
    val skipped: Int,
    val suiteOfProject: SuiteOfProject
)
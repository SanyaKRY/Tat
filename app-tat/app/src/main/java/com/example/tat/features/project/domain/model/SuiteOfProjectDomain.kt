package com.example.tat.features.project.domain.model

data class SuiteOfProjectDomain(
    val suiteName: String,
    val pass: Int,
    val failed: Int,
    val skipped: Int
)

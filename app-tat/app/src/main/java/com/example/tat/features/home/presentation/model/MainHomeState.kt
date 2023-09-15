package com.example.tat.features.home.presentation.model

import java.lang.Exception

data class MainHomeState(
    val userUi: UserUi? = null,
    val userProjectsUi: List<UserProjectUi> = emptyList(),
    val catsUi: List<CatUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: Exception? = null
)
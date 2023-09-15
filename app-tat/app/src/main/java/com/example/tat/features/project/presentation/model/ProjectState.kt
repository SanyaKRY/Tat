package com.example.tat.features.project.presentation.model

import com.example.tat.features.home.presentation.model.UserProjectUi
import com.example.tat.features.home.presentation.model.UserUi
import com.example.tat.features.project.domain.model.SuiteOfProjectDomain
import java.lang.Exception

data class ProjectState(
    val projectInfo: ProjectInfo? = null,
    val listOfSuiteProject: List<SuiteOfProject> = emptyList(),
    val isLoading: Boolean = false,
    val error: Exception? = null
)

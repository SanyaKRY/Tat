package com.example.tat.features.project.presentation.mapper

import com.example.tat.features.project.domain.model.ProjectInfoDomain
import com.example.tat.features.project.domain.model.SuiteOfProjectDomain
import com.example.tat.features.project.presentation.model.ProjectInfo
import com.example.tat.features.project.presentation.model.SuiteOfProject

object ProjectInfoDomainToUiMapper {
    fun map(type: ProjectInfoDomain): ProjectInfo {
        return ProjectInfo(
            numberOfSutes = type.numberOfSutes,
            numberOfTests = type.numberOfTests
        )
    }
}

object ListOfSuitesDomainToUiMapper {
    fun map(type: List<SuiteOfProjectDomain>): List<SuiteOfProject> {
        return type.map {
            SuiteOfProject(
                suiteName = it.suiteName,
                pass = it.pass,
                failed = it.failed,
                skipped= it.skipped
            )
        }
    }
}
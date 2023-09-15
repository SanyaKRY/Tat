package com.example.tat.features.project.domain.usecase

import com.example.tat.core.datatype.Result
import com.example.tat.features.project.domain.model.ProjectInfoDomain
import javax.inject.Inject

class GetInformationAboutProjectUseCase @Inject constructor() {

    suspend fun execute(): Result<ProjectInfoDomain> {
        return Result.Success(ProjectInfoDomain("14", "22"))
    }
}
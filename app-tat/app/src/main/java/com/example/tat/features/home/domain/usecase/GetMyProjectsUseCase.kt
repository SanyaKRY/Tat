package com.example.tat.features.home.domain.usecase

import com.example.tat.features.home.domain.HomeRepository
import com.example.tat.features.home.domain.model.UserProjectDomainApi
import com.example.tat.core.datatype.Result
import javax.inject.Inject

class GetMyProjectsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {

    suspend fun execute(): Result<List<UserProjectDomainApi>> {
        return homeRepository.getMyProjects()
    }
}

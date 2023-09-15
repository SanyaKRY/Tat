package com.example.tat.features.home.domain.usecase

import com.example.tat.features.home.domain.HomeRepository
import com.example.tat.features.home.domain.model.UserDomain
import com.example.tat.core.datatype.Result
import javax.inject.Inject

class GetInformationAboutMeUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {

    suspend fun execute(): Result<UserDomain> {
        return homeRepository.getInformationAboutMe()
    }
}
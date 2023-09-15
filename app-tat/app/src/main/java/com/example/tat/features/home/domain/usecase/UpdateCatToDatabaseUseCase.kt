package com.example.tat.features.home.domain.usecase

import com.example.tat.core.datatype.Result
import com.example.tat.features.home.domain.HomeRepository
import com.example.tat.features.home.domain.model.CatDomain
import javax.inject.Inject

class UpdateCatToDatabaseUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend fun execute(catDomain: CatDomain): Result<Unit> {
        return homeRepository.updateCatToDatabaseUseCase(catDomain)
    }
}
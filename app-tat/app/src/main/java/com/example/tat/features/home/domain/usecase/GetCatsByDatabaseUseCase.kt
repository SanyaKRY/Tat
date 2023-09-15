package com.example.tat.features.home.domain.usecase

import com.example.tat.core.datatype.Result
import com.example.tat.features.home.domain.HomeRepository
import com.example.tat.features.home.domain.model.CatDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatsByDatabaseUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    fun execute(): Flow<Result<List<CatDomain>>> {
        return homeRepository.getCatsByDatabase()
    }
}
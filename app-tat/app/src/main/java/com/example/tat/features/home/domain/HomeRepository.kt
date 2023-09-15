package com.example.tat.features.home.domain

import com.example.tat.features.home.domain.model.UserDomain
import com.example.tat.features.home.domain.model.UserProjectDomainApi
import com.example.tat.core.datatype.Result
import com.example.tat.features.home.domain.model.CatDomain
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getMyProjects(): Result<List<UserProjectDomainApi>>

    fun getCatsByDatabase(): Flow<Result<List<CatDomain>>>

    suspend fun updateCatToDatabaseUseCase(catDomain: CatDomain): Result<Unit>

    suspend fun getInformationAboutMe(): Result<UserDomain>
}
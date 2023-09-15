package com.example.tat.features.home.data.repository

import com.example.tat.features.home.data.datasource.api.UserNetworkDataSource
import com.example.tat.features.home.data.datasource.api.model.UserApi
import com.example.tat.features.home.data.datasource.api.model.UserProjectsApi
import com.example.tat.features.home.data.datasource.database.CatDatabaseDataSource
import com.example.tat.features.home.domain.HomeRepository
import com.example.tat.features.home.domain.model.UserDomain
import com.example.tat.features.home.domain.model.UserProjectDomainApi
import com.example.tat.core.datatype.Result
import com.example.tat.features.home.data.repository.mapper.*
import com.example.tat.features.home.domain.model.CatDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val userNetworkDataSource: UserNetworkDataSource,
    private val catDatabaseDataSource: CatDatabaseDataSource
) : HomeRepository {

    override suspend fun getMyProjects(): Result<List<UserProjectDomainApi>> {
        return when (val result: Result<UserProjectsApi> = userNetworkDataSource.getMyProjects()) {
            is Result.Success -> Result.Success(ProjectsApiToDomainMapper.map(result.data.projects))
            is Result.Error -> Result.Error(result.error)
            is Result.Loading -> Result.Loading
        }
    }

    override fun getCatsByDatabase(): Flow<Result<List<CatDomain>>> {
        return catDatabaseDataSource.getCats().map { result ->
            when (result) {
                is Result.Success -> Result.Success(CatsDatabaseToDomainMapper.map(result.data))
                is Result.Error -> Result.Error(result.error)
                is Result.Loading -> Result.Loading
            }
        }
    }

    override suspend fun updateCatToDatabaseUseCase(catDomain: CatDomain): Result<Unit> {
        return catDatabaseDataSource.updateCat(CatDomainToDatabaseMapper.map(catDomain))
    }

    override suspend fun getInformationAboutMe(): Result<UserDomain> {
        return when (val result: Result<UserApi> = userNetworkDataSource.getInformationAboutMe()) {
            is Result.Success -> Result.Success(UserApiToDomainMapper.map(result.data))
            is Result.Error -> Result.Error(result.error)
            is Result.Loading -> Result.Loading
        }
    }
}
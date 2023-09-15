package com.example.tat.features.home.data.repository.mapper

import com.example.tat.features.home.data.datasource.api.model.ProjectApi
import com.example.tat.features.home.data.datasource.api.model.UserApi
import com.example.tat.features.home.data.datasource.database.model.CatTableDatabase
import com.example.tat.features.home.domain.model.CatDomain
import com.example.tat.features.home.domain.model.UserDomain
import com.example.tat.features.home.domain.model.UserProjectDomainApi

object UserApiToDomainMapper {
    fun map(type: UserApi): UserDomain {
        return UserDomain(
            id = type.id,
            username = type.username,
            email = type.email,
            fullName = type.fullName,
            isActive = type.isActive,
            role = type.role
        )
    }
}

object ProjectsApiToDomainMapper {
    fun map(type: List<ProjectApi>): List<UserProjectDomainApi> {
        return type.map {
            UserProjectDomainApi(
                projectId = it.id,
                projectName = it.name
            )
        }
    }
}

object CatsDatabaseToDomainMapper {
    fun map(type: List<CatTableDatabase>): List<CatDomain> {
        return type.map {
            CatDomain(
                id = it.id,
                age = it.age,
                name = it.name
            )
        }
    }
}

object CatDomainToDatabaseMapper {
     fun map(type: CatDomain): CatTableDatabase {
        return CatTableDatabase(
            id = type.id,
            age = type.age,
            name = type.name
        )
    }
}
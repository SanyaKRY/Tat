package com.example.tat.features.home.presentation.mapper

import com.example.tat.features.home.domain.model.CatDomain
import com.example.tat.features.home.domain.model.UserDomain
import com.example.tat.features.home.domain.model.UserProjectDomainApi
import com.example.tat.features.home.presentation.model.CatUi
import com.example.tat.features.home.presentation.model.UserProjectUi
import com.example.tat.features.home.presentation.model.UserUi

object UserDomainToUiMapper {
    fun map(type: UserDomain): UserUi {
        return UserUi(
            id = type.id,
            username = type.username,
            email = type.email,
            fullName = type.fullName,
            isActive = type.isActive,
            role = type.role
        )
    }
}

object ProjectDomainToUiMapper {
    fun map(type: List<UserProjectDomainApi>): List<UserProjectUi> {
        return type.map {
            UserProjectUi(
                projectId = it.projectId,
                projectName = it.projectName
            )
        }
    }
}

object CatDomainToUiMapper {
    fun map(type: List<CatDomain>): List<CatUi> {
        return type.map {
            CatUi(
                id = it.id,
                age = it.age,
                name = it.name
            )
        }
    }
}

object CatUiToDomainMapper {
    fun map(type: CatUi): CatDomain {
        return CatDomain(
            id = type.id,
            age = type.age,
            name = type.name
        )
    }
}
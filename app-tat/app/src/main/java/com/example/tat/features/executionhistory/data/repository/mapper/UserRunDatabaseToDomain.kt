package com.example.tat.features.executionhistory.data.repository.mapper

import com.example.tat.features.executionhistory.data.datasource.database.model.UserRunTableDatabase
import com.example.tat.features.executionhistory.data.datasource.database.model.UserRunsTableDatabase
import com.example.tat.features.executionhistory.domain.model.UserRunDomain
import com.example.tat.features.executionhistory.domain.model.UserRunsDomain

object UserRunDatabaseToDomainMapper {
    fun map(type: List<UserRunTableDatabase>): List<UserRunDomain> {
        return type.map {
            UserRunDomain(
                runId = it.runId,
                projectName = it.projectName,
                entityName = it.entityName,
                entityId = it.entityId,
                status = it.status
            )
        }
    }
}

object UserRunsDatabaseToDomainMapper {
    fun map(type: UserRunsTableDatabase): UserRunsDomain {
        return UserRunsDomain(
            listOfUserRuns = type.listOfUserRuns.map {
                UserRunDomain(
                    runId = it.runId,
                    projectName = it.projectName,
                    entityName = it.entityName,
                    entityId = it.entityId,
                    status = it.status
                )
            }
        )
    }
}
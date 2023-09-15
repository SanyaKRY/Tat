package com.example.tat.features.executionhistory.data.datasource.database.mapper

import com.example.tat.features.executionhistory.data.datasource.database.model.UserRunTableDatabase
import com.example.tat.features.executionhistory.data.datasource.database.model.UserRunsTableDatabase
import com.example.tat.features.home.data.datasource.api.model.RunApi
import com.example.tat.features.home.data.datasource.api.model.UserRunsApi

object UserRunApiToDatabaseMapper {
    fun map(type: RunApi): UserRunTableDatabase {
        return UserRunTableDatabase(
            runId = type.runId,
            projectName = type.entity.projectName,
            entityName = type.entity.entityName,
            entityId = type.entity.entityId,
            status = type.status
        )
    }
}

object UserRunsApiToDatabaseMapper {
    fun map(type: UserRunsApi): UserRunsTableDatabase {
        return UserRunsTableDatabase(
            id = 0,
            listOfUserRuns = type.runs.map {
                UserRunTableDatabase(
                    runId = it.runId,
                    projectName = it.entity.projectName,
                    entityName = it.entity.entityName,
                    entityId = it.entity.entityId,
                    status = it.status
                )
            }
        )
    }
}
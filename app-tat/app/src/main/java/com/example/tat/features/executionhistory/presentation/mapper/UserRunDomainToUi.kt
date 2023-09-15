package com.example.tat.features.executionhistory.presentation.mapper

import com.example.tat.features.executionhistory.domain.model.UserRunDomain
import com.example.tat.features.executionhistory.domain.model.UserRunsDomain
import com.example.tat.features.executionhistory.presentation.model.StatusColorType
import com.example.tat.features.executionhistory.presentation.model.UserRunUi
import com.example.tat.features.executionhistory.presentation.model.UserRunsUi

object UserRunDomainToUiMapper {
    fun map(type: List<UserRunDomain>): List<UserRunUi> {
        return type.map {
            UserRunUi(
                runId = it.runId,
                projectName = it.projectName,
                entityName = it.entityName,
                entityId = it.entityId,
                status = it.status,
                statusColor = getColorType(it.status)
            )
        }
    }
}

object UserRunsDomainToUiMapper {
    fun map(type: UserRunsDomain): UserRunsUi {
        return UserRunsUi(
            listOfUserRuns = type.listOfUserRuns.map {
                UserRunUi(
                    runId = it.runId,
                    projectName = it.projectName,
                    entityName = it.entityName,
                    entityId = it.entityId,
                    status = it.status,
                    statusColor = getColorType(it.status)
                )
            }
        )
    }
}

private fun getColorType(status: String): StatusColorType {
    return when (status) {
        "PASSED" -> {
            StatusColorType.GREEN
        }
        "RUNNING", "QUEUED" -> StatusColorType.ORANGE
        "FAILED", "ERROR" -> StatusColorType.RED
        else -> {
            StatusColorType.GREY
        }
    }
}
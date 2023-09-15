package com.example.tat.features.project.domain.usecase

import com.example.tat.core.datatype.Result
import com.example.tat.features.project.domain.model.SuiteOfProjectDomain
import javax.inject.Inject

class GetListOfProjectSuitesUseCase @Inject constructor() {

    suspend fun execute(): Result<List<SuiteOfProjectDomain>> {
        val list = listOf<SuiteOfProjectDomain>(
            SuiteOfProjectDomain("ApiSuite",55,98,5),
            SuiteOfProjectDomain("AngryToy",9,55,7),
            SuiteOfProjectDomain("MySuite",99,30,5),
            SuiteOfProjectDomain("UpdateSuite",155,9,8),
            SuiteOfProjectDomain("ReBuild",45,3,9),
            SuiteOfProjectDomain("ChatGPT",87,98,98),
            SuiteOfProjectDomain("UISuite",98,85,4),
            SuiteOfProjectDomain("ApiCall",55,98,5),
            SuiteOfProjectDomain("GitHub",9,55,7),
            SuiteOfProjectDomain("UiRebuild",99,30,5),
            SuiteOfProjectDomain("Chrome",155,9,8),
            SuiteOfProjectDomain("WebDriver",45,3,9),
            SuiteOfProjectDomain("LogCat",87,98,98),
            SuiteOfProjectDomain("Profiler",98,85,4)
        )
        return Result.Success(list)
    }
}
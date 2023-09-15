package com.example.tat.features.project.domain.usecase

import android.util.Log
import com.example.tat.core.datatype.Result
import com.example.tat.features.project.domain.model.Chart
import com.example.tat.features.project.presentation.model.SuiteOfProject
import javax.inject.Inject

class GetChart @Inject constructor() {
    var counter: Int = 0

    suspend fun execute(suiteOfProject: SuiteOfProject, list: Int): Result<Chart> {
        val suiteOfProject: SuiteOfProject = suiteOfProject

counter++
        val chart: Chart = Chart(55, 29, 21, suiteOfProject)


        return when(suiteOfProject.suiteName) {
            "AngryToy" -> {
                if (counter==0) {
                    Result.Success( Chart(55+counter*3, 9+counter*2, 15+counter, suiteOfProject))
                } else if (counter==1) {
                    Result.Success( Chart(155,9,8, suiteOfProject))
                }else if (counter==2) {
                    Result.Success( Chart(55,98,5, suiteOfProject))
                }else if (counter==3) {
                    Result.Success( Chart(87,98,98, suiteOfProject))
                }else if (counter==4) {
                    Result.Success( Chart(45,13,19, suiteOfProject))
                }else if (counter==5) {
                    Result.Success( Chart(19,55,17, suiteOfProject))
                } else {
                    Result.Success(chart)
                }

            }
            else -> {Result.Success(chart)}
        }

//        return Result.Success(chart)
    }
}
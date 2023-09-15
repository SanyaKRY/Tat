package com.example.tat.features.project.presentation.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tat.core.datatype.Result
import com.example.tat.features.home.presentation.model.Event
import com.example.tat.features.project.domain.model.Chart
import com.example.tat.features.project.domain.model.ProjectInfoDomain
import com.example.tat.features.project.domain.model.SuiteOfProjectDomain
import com.example.tat.features.project.domain.usecase.GetChart
import com.example.tat.features.project.domain.usecase.GetInformationAboutProjectUseCase
import com.example.tat.features.project.domain.usecase.GetListOfProjectSuitesUseCase
import com.example.tat.features.project.presentation.mapper.ListOfSuitesDomainToUiMapper
import com.example.tat.features.project.presentation.mapper.ProjectInfoDomainToUiMapper
import com.example.tat.features.project.presentation.model.ChartState
import com.example.tat.features.project.presentation.model.ProjectState
import com.example.tat.features.project.presentation.model.SuiteOfProject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val getInformationAboutProjectUseCase: GetInformationAboutProjectUseCase,
    private val getListOfProjectSuitesUseCase: GetListOfProjectSuitesUseCase,
    private val getChart: GetChart
) : ViewModel() {

    private val _chartStateFlow: MutableStateFlow<ChartState> =
        MutableStateFlow(ChartState(isLoading = true, isVisableChart = true))
    val chartStateFlow: Flow<ChartState>
        get() = _chartStateFlow

    private val _stateFlow: MutableStateFlow<ProjectState> =
        MutableStateFlow(ProjectState(isLoading = true))
    val stateFlow: Flow<ProjectState>
        get() = _stateFlow

    private val projectEventChannel = Channel<Event>(Channel.BUFFERED)
    val projectEventsFlow = projectEventChannel.receiveAsFlow()

    init {
        getListOfProjectSuites()
        getInformationAboutProject()
    }

    fun getChart(suiteOfProject: SuiteOfProject, list: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _chartStateFlow.value =
                _chartStateFlow.value.copy(isLoading = true, isVisableChart = false)
            delay(2_000)
            val result: Result<Chart> = getChart.execute(suiteOfProject, list)
            when (result) {
                is Result.Success -> {
                    _chartStateFlow.value =
                        _chartStateFlow.value.copy(chart = result.data, isLoading = false)
                }
                is Result.Error -> {
                    // very sad :(
                }
                is Result.Loading -> {
                    // very sad :(
                }
            }
        }
    }

    private fun getListOfProjectSuites() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(3_000)
            val result: Result<List<SuiteOfProjectDomain>> = getListOfProjectSuitesUseCase.execute()

            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> {
                        val list = ListOfSuitesDomainToUiMapper.map(result.data)
                        Log.d("dfsddfsdff", "${list.size}")
                        _stateFlow.value =
                            _stateFlow.value.copy(listOfSuiteProject = list, isLoading = false)
                    }
                    is Result.Error -> {
                        _stateFlow.value =
                            _stateFlow.value.copy(error = result.error, isLoading = false)
                    }
                    is Result.Loading -> {
                        // very sad :(
                    }
                }
            }
        }
    }

    private fun getInformationAboutProject() {
        viewModelScope.launch(Dispatchers.IO) {
            val result: Result<ProjectInfoDomain> = getInformationAboutProjectUseCase.execute()
            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> {
                        val info = ProjectInfoDomainToUiMapper.map(result.data)
                        _stateFlow.value =
                            _stateFlow.value.copy(projectInfo = info, isLoading = false)
                    }
                    is Result.Error -> {
                        _stateFlow.value =
                            _stateFlow.value.copy(error = result.error, isLoading = false)
                    }
                    is Result.Loading -> {
                        // very sad :(
                    }
                }
            }
        }
    }
}
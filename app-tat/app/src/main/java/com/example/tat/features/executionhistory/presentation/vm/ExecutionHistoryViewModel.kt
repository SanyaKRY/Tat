package com.example.tat.features.executionhistory.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tat.core.datatype.Result
import com.example.tat.features.executionhistory.domain.usecase.GetUserRunsByDatabaseUseCase
import com.example.tat.features.executionhistory.presentation.mapper.UserRunsDomainToUiMapper
import com.example.tat.features.executionhistory.presentation.model.HistoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExecutionHistoryViewModel @Inject constructor(
    private val getUserRunsByDatabaseUseCase: GetUserRunsByDatabaseUseCase,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<HistoryState> =
        MutableStateFlow(HistoryState(isLoading = true))
    val stateFlow: Flow<HistoryState>
        get() = _stateFlow

    init {
        getUserRunsByDatabase()
    }

    private fun getUserRunsByDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            val flow = getUserRunsByDatabaseUseCase.execute()
            withContext(Dispatchers.Main) {
                flow.collect { result ->
                    when (result) {
                        is Result.Success -> {
                            val list = UserRunsDomainToUiMapper.map(result.data)
                            _stateFlow.value =
                                _stateFlow.value.copy(userRunsUi = list, isLoading = false)
                        }
                        is Result.Error -> {
                            // TODO
                        }
                        is Result.Loading -> {
                            // TODO
                        }
                    }
                }
            }
        }
    }
}
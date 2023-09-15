package com.example.tat.features.home.presentation.vm

import androidx.lifecycle.viewModelScope
import com.example.tat.core.datatype.Result
import com.example.tat.features.home.domain.model.UserDomain
import com.example.tat.features.home.domain.model.UserProjectDomainApi
import com.example.tat.features.home.domain.usecase.*
import com.example.tat.features.logout.domain.usecase.LogoutUseCase
import com.example.tat.features.home.presentation.event.MainHomeEvent
import com.example.tat.features.home.presentation.event.UpdateCatToDatabase
import com.example.tat.features.logout.vm.LogoutViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.tat.features.home.presentation.mapper.*
import com.example.tat.features.home.presentation.model.*
import com.example.tat.utils.exceptions.UPDATED
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCatsByDatabaseUseCase: GetCatsByDatabaseUseCase,
    private val updateCatToDatabaseUseCase: UpdateCatToDatabaseUseCase,
    private val getInformationAboutMeUseCase: GetInformationAboutMeUseCase,
    private val getMyProjectsUseCase: GetMyProjectsUseCase,
    logoutUseCase: LogoutUseCase
) : LogoutViewModel(logoutUseCase) {

    private val _stateFlow: MutableStateFlow<MainHomeState> =
        MutableStateFlow(MainHomeState(isLoading = true))
    val stateFlow: Flow<MainHomeState>
        get() = _stateFlow

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    init {
        getCatsByDatabase()
        getMyProjectsByApi()
        requestInformationAboutMeByApi()
    }

    fun handleIntent(event: MainHomeEvent) {
        when (event) {
            is UpdateCatToDatabase -> updateCatToDatabase(catUi = event.catUi)
        }
    }

    private fun updateCatToDatabase(catUi: CatUi) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = updateCatToDatabaseUseCase.execute(
                CatUiToDomainMapper.map(catUi)
            )
            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> {
                        eventChannel.send(Event.ShowToast(UPDATED))
                    }
                    is Result.Error -> {
                        _stateFlow.value = _stateFlow.value.copy(error = result.error)
                    }
                    is Result.Loading -> {
                        // very sad :(
                    }
                }
            }
        }
    }

    private fun requestInformationAboutMeByApi() {
        viewModelScope.launch(Dispatchers.IO) {
//            delay(3_000)
            val result: Result<UserDomain> = getInformationAboutMeUseCase.execute()
            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> {
                        val userUiInfo: UserUi = UserDomainToUiMapper.map(result.data)
                        _stateFlow.value =
                            _stateFlow.value.copy(userUi = userUiInfo, isLoading = false)
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

    private fun getMyProjectsByApi() {
        viewModelScope.launch(Dispatchers.IO) {
//            delay(3_000)
            val result: Result<List<UserProjectDomainApi>> = getMyProjectsUseCase.execute()
            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> {
                        val list = ProjectDomainToUiMapper.map(result.data)
                        _stateFlow.value =
                            _stateFlow.value.copy(userProjectsUi = list, isLoading = false)
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

    private fun getCatsByDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(2_000)
            val flow = getCatsByDatabaseUseCase.execute()
            withContext(Dispatchers.Main) {
                flow.collect { result ->
                    when (result) {
                        is Result.Success -> {
                            val list = CatDomainToUiMapper.map(result.data)
                            _stateFlow.value =
                                _stateFlow.value.copy(catsUi = list, isLoading = false)
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
}
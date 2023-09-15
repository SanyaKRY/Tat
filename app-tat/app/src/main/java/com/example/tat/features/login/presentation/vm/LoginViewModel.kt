package com.example.tat.features.login.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tat.features.login.domain.usecase.LoginUseCase
import com.example.tat.features.login.data.datasource.api.model.UserAuthApi
import com.example.tat.features.login.data.datasource.api.model.LoginResponseApi
import com.example.tat.core.datatype.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginResponse: MutableLiveData<Result<LoginResponseApi>> = MutableLiveData()
    val loginResponse: LiveData<Result<LoginResponseApi>>
        get() = _loginResponse

    fun login(userAuthApi: UserAuthApi) {
        _loginResponse.value = Result.Loading
        viewModelScope.launch(Dispatchers.IO) {
            delay(2_000)
            val loginResponse = loginUseCase.execute(userAuthApi)
            withContext(Dispatchers.Main) {
                _loginResponse.value = loginResponse
            }
        }
    }
}
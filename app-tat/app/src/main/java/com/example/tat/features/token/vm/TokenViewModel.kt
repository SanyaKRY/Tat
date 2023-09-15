package com.example.tat.features.token.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tat.features.token.manager.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(
    private val tokenManager: TokenManager,
): ViewModel() {

    val accessToken = MutableLiveData<String>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.getAccessToken().collect {
                withContext(Dispatchers.Main) {
                    accessToken.value = it
                }
            }
        }
    }

    fun saveToken(accessToken: String, refreshToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.saveToken(accessToken, refreshToken)
        }
    }

    fun deleteToken() {
        viewModelScope.launch(Dispatchers.IO) {
            tokenManager.deleteToken()
        }
    }
}
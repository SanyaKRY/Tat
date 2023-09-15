package com.example.tat.core.datatype

import java.lang.Exception

sealed class Result<out T> {

    data class Success<T>(val data: T) : Result<T>()
    data class Error(val error: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
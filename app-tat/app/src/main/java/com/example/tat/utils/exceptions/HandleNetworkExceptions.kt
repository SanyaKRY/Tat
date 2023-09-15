package com.example.tat.utils.exceptions

import retrofit2.HttpException
import java.lang.Exception

fun handleNetworkExceptions(ex: Exception): Exception {
    return when (ex) {
        is HttpException -> apiErrorFromCodeException(ex.code())
        else -> GenericNetworkException()
    }
}

private fun apiErrorFromCodeException(code: Int): Exception {
    return if (code == 401) {
        UnauthorizedException()
    } else {
        GenericNetworkException()
    }
}
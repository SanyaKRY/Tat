package com.example.tat.features.home.presentation.model

sealed class Event {
    data class ShowToast(val text: String): Event()
}

package com.example.tat.features.home.presentation.model

data class UserUi(
    val id: Int,
    val username: String,
    val email: String,
    val fullName: String,
    val isActive: Boolean,
    val role: String
)

package com.example.tat.features.home.domain.model

data class UserDomain(
    val id: Int,
    val username: String,
    val email: String,
    val fullName: String,
    val isActive: Boolean,
    val role: String
)

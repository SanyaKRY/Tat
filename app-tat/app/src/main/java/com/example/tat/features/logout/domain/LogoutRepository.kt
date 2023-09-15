package com.example.tat.features.logout.domain

interface LogoutRepository {

    suspend fun logout()
}
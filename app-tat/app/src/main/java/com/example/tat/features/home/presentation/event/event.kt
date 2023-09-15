package com.example.tat.features.home.presentation.event

import com.example.tat.features.home.presentation.model.CatUi

interface MainHomeEvent

class UpdateCatToDatabase(val catUi: CatUi) : MainHomeEvent
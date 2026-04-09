package com.example.sebwave.features.map.domain.entities

import com.example.sebwave.features.dashboard.domain.entities.SemaphoreStatus

data class SemaphoreMarker(
    val id: Int,
    val name: String,
    val street: String,
    val latitude: Double,
    val longitude: Double,
    val status: SemaphoreStatus,
    val currentCongestion: Int
)

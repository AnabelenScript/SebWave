package com.example.sebwave.features.map.domain.repositories

import com.example.sebwave.features.map.domain.entities.SemaphoreMarker
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    fun getSemaphoreMarkers(): Flow<Result<List<SemaphoreMarker>>>
}

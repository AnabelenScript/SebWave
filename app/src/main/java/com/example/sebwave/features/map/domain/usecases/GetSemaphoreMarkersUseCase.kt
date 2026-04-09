package com.example.sebwave.features.map.domain.usecases

import com.example.sebwave.features.map.domain.entities.SemaphoreMarker
import com.example.sebwave.features.map.domain.repositories.MapRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSemaphoreMarkersUseCase @Inject constructor(
    private val mapRepository: MapRepository
) {
    operator fun invoke(): Flow<Result<List<SemaphoreMarker>>> =
        mapRepository.getSemaphoreMarkers()
}

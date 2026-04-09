package com.example.sebwave.features.map.data.repositories

import com.example.sebwave.features.map.data.datasources.mapper.toDomain
import com.example.sebwave.features.map.data.datasources.remote.MapApiService
import com.example.sebwave.features.map.domain.entities.SemaphoreMarker
import com.example.sebwave.features.map.domain.repositories.MapRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val mapApiService: MapApiService
) : MapRepository {

    override fun getSemaphoreMarkers(): Flow<Result<List<SemaphoreMarker>>> = flow {
        try {
            val markers = mapApiService.getSemaphoreMarkers().toDomain()
            emit(Result.success(markers))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}

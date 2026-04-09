package com.example.sebwave.features.map.data.datasources.remote

import com.example.sebwave.features.map.data.datasources.model.SemaphoreMarkerDto
import retrofit2.http.GET

interface MapApiService {
    @GET("semaphores/markers")
    suspend fun getSemaphoreMarkers(): List<SemaphoreMarkerDto>
}

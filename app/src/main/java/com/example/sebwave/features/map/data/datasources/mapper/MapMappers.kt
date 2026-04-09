package com.example.sebwave.features.map.data.datasources.mapper

import com.example.sebwave.features.dashboard.domain.entities.SemaphoreStatus
import com.example.sebwave.features.map.data.datasources.model.SemaphoreMarkerDto
import com.example.sebwave.features.map.domain.entities.SemaphoreMarker

fun SemaphoreMarkerDto.toDomain(): SemaphoreMarker {
    val mappedStatus = when (status.uppercase()) {
        "CONECTADO"    -> SemaphoreStatus.CONECTADO
        "FALLIDO"      -> SemaphoreStatus.FALLIDO
        else           -> SemaphoreStatus.DESCONECTADO
    }
    return SemaphoreMarker(
        id               = id,
        name             = name,
        street           = street,
        latitude         = latitude,
        longitude        = longitude,
        status           = mappedStatus,
        currentCongestion = currentCongestion
    )
}

fun List<SemaphoreMarkerDto>.toDomain(): List<SemaphoreMarker> = map { it.toDomain() }

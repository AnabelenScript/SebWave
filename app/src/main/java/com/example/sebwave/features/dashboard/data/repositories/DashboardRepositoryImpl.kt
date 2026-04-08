package com.example.sebwave.features.dashboard.data.repositories

import com.example.sebwave.features.dashboard.data.datasources.remote.DashboardApiService
import com.example.sebwave.features.dashboard.data.mappers.toDomain
import com.example.sebwave.features.dashboard.domain.entities.*
import com.example.sebwave.features.dashboard.domain.repositories.DashboardRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val apiService: DashboardApiService
) : DashboardRepository {

    override suspend fun getDashboardStats(): Result<DashboardStats> {
        delay(800)
        // Lógica de conexión a API (comentada para usar simulación por ahora)
        /*
        return try {
            val response = apiService.getStats()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.toDomain())
            } else {
                Result.failure(Exception("Error al obtener estadísticas"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        */
        
        // Datos simulados (según el esquema y la imagen)
        return Result.success(
            DashboardStats(
                processedVehicles = "42.5K",
                savedTimeHours = 150,
                totalSemaphores = 128,
                activeSemaphores = 12,
                redSemaphores = 3,
                yellowSemaphores = 3,
                greenSemaphores = 3
            )
        )
    }

    override suspend fun getSemaphores(): Result<List<Semaphore>> {
        delay(1000)
        // Lógica de conexión a API (comentada para usar simulación por ahora)
        /*
        return try {
            val response = apiService.getSemaphores()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.map { it.toDomain() })
            } else {
                Result.failure(Exception("Error al obtener semáforos"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        */

        // Datos simulados (según el esquema y la imagen)
        return Result.success(
            listOf(
                Semaphore(
                    id = 1,
                    name = "INT-001",
                    street = "Av. Reforma & Insurgentes",
                    maxCongestion = 0.9,
                    maxTimeGreenLight = 60,
                    status = SemaphoreStatus.CONECTADO,
                    latitude = 19.4326,
                    longitude = -99.1332,
                    currentCongestion = 45,
                    currentGreenTime = 32
                ),
                Semaphore(
                    id = 2,
                    name = "INT-0012",
                    street = "Av. Costa rica & Insurgentes",
                    maxCongestion = 0.8,
                    maxTimeGreenLight = 45,
                    status = SemaphoreStatus.FALLIDO,
                    latitude = 19.4350,
                    longitude = -99.1400,
                    currentCongestion = 38,
                    currentGreenTime = 12
                ),
                Semaphore(
                    id = 3,
                    name = "INT-02122",
                    street = "Av. Reforma & Insurgentes",
                    maxCongestion = 0.7,
                    maxTimeGreenLight = 50,
                    status = SemaphoreStatus.DESCONECTADO,
                    latitude = 19.4400,
                    longitude = -99.1500,
                    currentCongestion = 45,
                    currentGreenTime = 22
                )
            )
        )
    }
}

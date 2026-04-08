package com.example.sebwave.features.users.data.repositories

import com.example.sebwave.core.storage.DataStorage
import com.example.sebwave.core.storage.UserSession
import com.example.sebwave.features.users.data.datasources.model.AuthResponseDto
import com.example.sebwave.features.users.data.datasources.model.UserDto
import com.example.sebwave.features.users.domain.entities.User
import com.example.sebwave.features.users.domain.entities.UserRole
import com.example.sebwave.features.users.domain.repositories.AuthRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataStorage: DataStorage
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        // Simulación de delay de red
        delay(1000)

        // TODO: Implementar llamada real a la API aquí
        /*
        val response = api.login(LoginRequestDto(email, password))
        if (response.isSuccessful) {
            val authResponse = response.body()
            authResponse?.let { 
                dataStorage.saveLoginResponse(it)
                val user = it.user.toDomain()
                UserSession.login(user.id, user.username, user.role.name, it.token)
                return Result.success(user)
            }
        }
        */

        // 1. Verificar credenciales "hardcoded" de prueba
        if (email == "admin@sebwave.com" && password == "admin123") {
            val userDto = UserDto(1, "AdminSebWave", email, "admin")
            val authResponse = AuthResponseDto(userDto, "simulated_token_123")
            dataStorage.saveLoginResponse(authResponse)
            val user = User(id = 1, username = "AdminSebWave", email = email, role = UserRole.ADMIN)
            UserSession.login(user.id, user.username, user.role.name, authResponse.token)
            return Result.success(user)
        } 
        
        if (email == "user@sebwave.com" && password == "user123") {
            val userDto = UserDto(2, "RegularUser", email, "regular")
            val authResponse = AuthResponseDto(userDto, "simulated_token_456")
            dataStorage.saveLoginResponse(authResponse)
            val user = User(id = 2, username = "RegularUser", email = email, role = UserRole.REGULAR)
            UserSession.login(user.id, user.username, user.role.name, authResponse.token)
            return Result.success(user)
        }

        // 2. Verificar si hay un usuario registrado localmente que coincida
        val savedResponse = dataStorage.getLoginResponse()
        if (savedResponse != null && savedResponse.user.email == email) {
            // Para la simulación, si el email coincide con el guardado (registro reciente), permitimos el login
            val user = User(
                id = savedResponse.user.id,
                username = savedResponse.user.username,
                email = savedResponse.user.email,
                role = if (savedResponse.user.role == "admin") UserRole.ADMIN else UserRole.REGULAR
            )
            UserSession.login(user.id, user.username, user.role.name, savedResponse.token)
            return Result.success(user)
        }

        return Result.failure(Exception("Credenciales inválidas (Simulado)"))
    }

    override suspend fun register(username: String, email: String, password: String): Result<User> {
        delay(1000)
        // TODO: Implementar registro real en la API
        /*
        val response = api.register(RegisterRequestDto(username, email, password))
        if (response.isSuccessful) {
             // Lógica similar al login para guardar sesión
        }
        */
        
        val user = User(id = 3, username = username, email = email, role = UserRole.REGULAR)
        val userDto = UserDto(user.id, user.username, user.email, "regular")
        val authResponse = AuthResponseDto(userDto, "simulated_token_reg")
        
        // Guardamos en local storage para que el login pueda encontrarlo después
        dataStorage.saveLoginResponse(authResponse)
        UserSession.login(user.id, user.username, user.role.name)
        
        return Result.success(user)
    }

    override suspend fun logout() {
        dataStorage.clearAll()
        UserSession.logout()
    }
}

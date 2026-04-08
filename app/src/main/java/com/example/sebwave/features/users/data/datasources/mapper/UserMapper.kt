package com.example.sebwave.features.users.data.datasources.mapper

import com.example.sebwave.features.users.data.datasources.model.UserDto
import com.example.sebwave.features.users.domain.entities.User
import com.example.sebwave.features.users.domain.entities.UserRole

fun UserDto.toDomain(): User {
    return User(
        id = id,
        username = username,
        email = email,
        role = try {
            UserRole.valueOf(role.uppercase())
        } catch (e: Exception) {
            UserRole.REGULAR
        }
    )
}

// Nota: He eliminado las referencias a UserEntity por ahora ya que no se proporcionó el esquema de Room,
// pero si decides usar Room, deberás actualizar UserEntity con los mismos campos.

package com.example.sebwave.core.storage

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object UserSession {

    private val _userId = MutableStateFlow<Int?>(null)
    val userId = _userId.asStateFlow()

    private val _username = MutableStateFlow<String?>(null)
    val username = _username.asStateFlow()

    private val _role = MutableStateFlow<String?>(null)
    val role = _role.asStateFlow()

    private val _token = MutableStateFlow<String?>(null)
    val token = _token.asStateFlow()

    fun login(
        id: Int,
        username: String,
        role: String,
        token: String? = null
    ) {
        _userId.value = id
        _username.value = username
        _role.value = role
        _token.value = token
    }

    fun logout() {
        _userId.value = null
        _username.value = null
        _role.value = null
        _token.value = null
    }

    fun isLoggedIn(): Boolean {
        return _userId.value != null
    }

    fun getUserId(): Int? {
        return _userId.value
    }

    fun getRole(): String? {
        return _role.value
    }
}

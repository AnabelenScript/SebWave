package com.example.sebwave.core.storage

import android.content.Context
import android.content.SharedPreferences
import com.example.sebwave.features.users.data.datasources.model.AuthResponseDto
import com.google.gson.Gson

class DataStorage(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("sebwave_prefs", Context.MODE_PRIVATE)

    private val gson = Gson()

    fun saveLoginResponse(response: AuthResponseDto) {
        val json = gson.toJson(response)
        prefs.edit().putString("login_response", json).apply()
    }

    fun getLoginResponse(): AuthResponseDto? {
        val json = prefs.getString("login_response", null)
        return json?.let { gson.fromJson(it, AuthResponseDto::class.java) }
    }

    fun clearLoginResponse() {
        prefs.edit().remove("login_response").apply()
    }

    fun clearAll() {
        prefs.edit().clear().apply()
    }
}
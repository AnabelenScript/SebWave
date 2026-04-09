package com.example.sebwave.features.map.data.datasources.model

import com.google.gson.annotations.SerializedName

data class SemaphoreMarkerDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("street") val street: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("status") val status: String,
    @SerializedName("current_congestion") val currentCongestion: Int
)

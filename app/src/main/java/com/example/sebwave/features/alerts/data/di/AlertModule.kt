package com.example.sebwave.features.alerts.data.di

import com.example.sebwave.features.alerts.data.repositories.MockAlertRepository
import com.example.sebwave.features.alerts.domain.repositories.AlertRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AlertModule {

    @Binds
    @Singleton
    abstract fun bindAlertRepository(
        mockAlertRepository: MockAlertRepository
    ): AlertRepository
}

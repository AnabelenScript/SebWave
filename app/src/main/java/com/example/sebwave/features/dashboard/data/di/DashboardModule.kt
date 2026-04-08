package com.example.sebwave.features.dashboard.data.di

import com.example.sebwave.core.di.APIMainRetrofit
import com.example.sebwave.features.dashboard.data.datasources.remote.DashboardApiService
import com.example.sebwave.features.dashboard.data.repositories.DashboardRepositoryImpl
import com.example.sebwave.features.dashboard.domain.repositories.DashboardRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DashboardModule {

    @Binds
    @Singleton
    abstract fun bindDashboardRepository(
        dashboardRepositoryImpl: DashboardRepositoryImpl
    ): DashboardRepository

    companion object {
        @Provides
        @Singleton
        fun provideDashboardApiService(@APIMainRetrofit retrofit: Retrofit): DashboardApiService {
            return retrofit.create(DashboardApiService::class.java)
        }
    }
}

package com.example.sebwave.features.map.data.di

import com.example.sebwave.core.di.APIMainRetrofit
import com.example.sebwave.features.map.data.datasources.remote.MapApiService
import com.example.sebwave.features.map.data.repositories.MapRepositoryImpl
import com.example.sebwave.features.map.domain.repositories.MapRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MapModule {

    @Binds
    @Singleton
    abstract fun bindMapRepository(
        mapRepositoryImpl: MapRepositoryImpl
    ): MapRepository

    companion object {
        @Provides
        @Singleton
        fun provideMapApiService(@APIMainRetrofit retrofit: Retrofit): MapApiService {
            return retrofit.create(MapApiService::class.java)
        }
    }
}

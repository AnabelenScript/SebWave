package com.example.sebwave.core.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    // Se ha limpiado el módulo de Room temporalmente para permitir la compilación con KSP
    // ya que no existen los DAOs ni la AppDatabase en el proyecto actual de SebWave.
    // Descomenta y configura cuando implementes la base de datos local.
}
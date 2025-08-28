package com.ramruizni.deepseekpokeappfour.di.modules

import com.ramruizni.deepseekpokeappfour.pokemon.datasource.PokemonDataSource
import com.ramruizni.deepseekpokeappfour.pokemon.domain.PokemonRepository
import com.ramruizni.deepseekpokeappfour.pokemon.infrastructure.PokemonRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonInfrastructureModule {
    
    @Singleton
    @Provides
    fun providesPokemonRepository(
        dataSource: PokemonDataSource
    ): PokemonRepository = PokemonRepositoryImpl(dataSource)
}
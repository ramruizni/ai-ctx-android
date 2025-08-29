package com.example.deepseekpokeappeleven.di.modules

import com.example.deepseekpokeappeleven.pokemon.datasource.PokemonDataSource
import com.example.deepseekpokeappeleven.pokemon.domain.repositories.PokemonRepository
import com.example.deepseekpokeappeleven.pokemon.infrastructure.repositories.PokemonRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonInfrastructureModule {

    @Provides
    @Singleton
    fun providesPokemonRepository(
        dataSource: PokemonDataSource
    ): PokemonRepository = PokemonRepositoryImpl(dataSource)
}
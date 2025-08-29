package com.example.deepseekpokeappten.di.modules

import com.example.deepseekpokeappten.pokemon.datasource.PokemonDataSource
import com.example.deepseekpokeappten.pokemon.domain.PokemonRepository
import com.example.deepseekpokeappten.pokemon.infrastructure.PokemonRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonRepositoryModule {

    @Provides
    @Singleton
    fun providePokemonRepository(
        dataSource: PokemonDataSource
    ): PokemonRepository {
        return PokemonRepositoryImpl(dataSource)
    }
}
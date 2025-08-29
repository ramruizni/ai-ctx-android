package com.example.deepseekpokeapptwelve.di.modules

import com.example.deepseekpokeapptwelve.pokemon.domain.repository.PokemonRepository
import com.example.deepseekpokeapptwelve.pokemon.datasource.PokemonDataSource
import com.example.deepseekpokeapptwelve.pokemon.infrastructure.repository.PokemonRepositoryImpl
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
    fun providePokemonRepository(pokemonDataSource: PokemonDataSource): PokemonRepository {
        return PokemonRepositoryImpl(pokemonDataSource)
    }
}
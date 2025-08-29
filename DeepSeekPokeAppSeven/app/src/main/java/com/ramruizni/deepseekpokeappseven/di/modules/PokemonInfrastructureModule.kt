package com.ramruizni.deepseekpokeappseven.di.modules

import com.ramruizni.deepseekpokeappseven.pokemon.datasource.PokemonDataSource
import com.ramruizni.deepseekpokeappseven.pokemon.domain.PokemonRepository
import com.ramruizni.deepseekpokeappseven.pokemon.infrastructure.PokemonRepositoryImpl
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
    fun providePokemonRepository(
        pokemonDataSource: PokemonDataSource
    ): PokemonRepository {
        return PokemonRepositoryImpl(pokemonDataSource)
    }
}
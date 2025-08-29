package com.ramruizni.deepseekpokeappeight.pokemon.infrastructure.di

import com.ramruizni.deepseekpokeappeight.pokemon.datasource.PokemonDataSource
import com.ramruizni.deepseekpokeappeight.pokemon.domain.PokemonRepository
import com.ramruizni.deepseekpokeappeight.pokemon.infrastructure.PokemonRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module providing infrastructure layer dependencies for Pokemon feature.
 * Provides repository implementation following manual instantiation pattern.
 */
@Module
@InstallIn(SingletonComponent::class)
object PokemonInfrastructureModule {
    
    /**
     * Provides PokemonRepository implementation with manual instantiation
     * @param pokemonDataSource The Pokemon data source implementation
     * @return PokemonRepository interface implementation
     */
    @Provides
    @Singleton
    fun providePokemonRepository(
        pokemonDataSource: PokemonDataSource
    ): PokemonRepository {
        return PokemonRepositoryImpl(pokemonDataSource)
    }
}
package com.ramruizni.deepseekpokeappeight.pokemon.datasource.di

import com.ramruizni.deepseekpokeappeight.database.DeepSeekPokeAppEightDatabase
import com.ramruizni.deepseekpokeappeight.pokemon.datasource.PokemonDataSource
import com.ramruizni.deepseekpokeappeight.pokemon.datasource.PokemonDataSourceImpl
import com.ramruizni.deepseekpokeappeight.pokemon.local.PokemonDao
import com.ramruizni.deepseekpokeappeight.pokemon.datasource.remote.PokemonApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module providing data source layer dependencies for Pokemon feature.
 * Provides data source implementations and database access objects following manual instantiation pattern.
 */
@Module
@InstallIn(SingletonComponent::class)
object PokemonDataSourceModule {
    
    /**
     * Provides PokemonDao from the main database
     * @param database The main application database instance
     * @return PokemonDao for local database operations
     */
    @Provides
    fun providePokemonDao(database: DeepSeekPokeAppEightDatabase): PokemonDao {
        return database.pokemonDao()
    }
    
    /**
     * Provides PokemonDataSource implementation with manual instantiation
     * Combines API service and local database access for comprehensive data operations
     * @param apiService API service for remote Pokemon data
     * @param pokemonDao Local database access object
     * @return PokemonDataSource interface implementation
     */
    @Provides
    @Singleton
    fun providePokemonDataSource(
        apiService: PokemonApiService,
        pokemonDao: PokemonDao
    ): PokemonDataSource {
        return PokemonDataSourceImpl(apiService, pokemonDao)
    }
}
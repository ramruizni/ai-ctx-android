package com.ramruizni.deepseekpokeappseven.di.modules

import com.ramruizni.deepseekpokeappseven.pokemon.datasource.PokemonDataSource
import com.ramruizni.deepseekpokeappseven.pokemon.datasource.PokemonDataSourceImpl
import com.ramruizni.deepseekpokeappseven.database.entities.PokemonDao
import com.ramruizni.deepseekpokeappseven.pokemon.datasource.remote.PokemonApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonDataSourceModule {
    
    @Provides
    @Singleton
    fun providePokemonDataSource(
        pokemonDao: PokemonDao,
        pokemonApiService: PokemonApiService
    ): PokemonDataSource {
        return PokemonDataSourceImpl(pokemonDao, pokemonApiService)
    }
}
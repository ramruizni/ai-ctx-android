package com.ramruizni.deepseekpokeappsix.pokemon.datasource.di

import com.ramruizni.deepseekpokeappsix.database.DeepSeekPokeAppSixDatabase
import com.ramruizni.deepseekpokeappsix.pokemon.datasource.PokemonDataSource
import com.ramruizni.deepseekpokeappsix.pokemon.datasource.PokemonDataSourceImpl
import com.ramruizni.deepseekpokeappsix.database.daos.pokemon.PokemonDao
import com.ramruizni.deepseekpokeappsix.pokemon.datasource.network.PokemonApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonDataSourceModule {

    @Singleton
    @Provides
    fun providePokemonDao(
        database: DeepSeekPokeAppSixDatabase
    ): PokemonDao {
        return database.pokemonDao()
    }

    @Singleton
    @Provides
    fun providePokemonDataSource(
        pokemonDao: PokemonDao,
        pokemonApiService: PokemonApiService
    ): PokemonDataSource {
        return PokemonDataSourceImpl(pokemonDao, pokemonApiService)
    }
}
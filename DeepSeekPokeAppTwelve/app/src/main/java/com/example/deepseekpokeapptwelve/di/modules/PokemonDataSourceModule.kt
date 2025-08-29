package com.example.deepseekpokeapptwelve.di.modules

import com.example.deepseekpokeapptwelve.database.AppDatabase
import com.example.deepseekpokeapptwelve.pokemon.datasource.PokemonDataSource
import com.example.deepseekpokeapptwelve.pokemon.datasource.PokemonDataSourceImpl
import com.example.deepseekpokeapptwelve.pokemon.datasource.daos.PokemonDao
import com.example.deepseekpokeapptwelve.pokemon.datasource.network.PokemonApiService
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
        appDatabase: AppDatabase
    ): PokemonDao {
        return appDatabase.pokemonDao()
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
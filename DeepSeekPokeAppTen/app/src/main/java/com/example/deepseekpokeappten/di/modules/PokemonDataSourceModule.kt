package com.example.deepseekpokeappten.di.modules

import com.example.deepseekpokeappten.database.daos.PokemonDao
import com.example.deepseekpokeappten.pokemon.datasource.PokemonDataSource
import com.example.deepseekpokeappten.pokemon.datasource.PokemonDataSourceImpl
import com.example.deepseekpokeappten.pokemon.datasource.remote.PokemonApiService
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
        apiService: PokemonApiService,
        dao: PokemonDao
    ): PokemonDataSource {
        return PokemonDataSourceImpl(apiService, dao)
    }
}
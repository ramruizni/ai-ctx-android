package com.example.deepseekpokeappeleven.di.modules

import com.example.deepseekpokeappeleven.pokemon.datasource.PokemonDataSource
import com.example.deepseekpokeappeleven.pokemon.datasource.PokemonDataSourceImpl
import com.example.deepseekpokeappeleven.pokemon.datasource.api.PokeApiService
import com.example.deepseekpokeappeleven.pokemon.datasource.local.PokemonLocalDataSource
import com.example.deepseekpokeappeleven.pokemon.datasource.remote.PokemonRemoteDataSource
import com.example.deepseekpokeappeleven.pokemon.datasource.remote.PokemonRemoteDataSourceImpl
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
    fun providesPokemonRemoteDataSource(
        pokeApiService: PokeApiService
    ): PokemonRemoteDataSource = PokemonRemoteDataSourceImpl(pokeApiService)

    @Provides
    @Singleton
    fun providesPokemonDataSource(
        localDataSource: PokemonLocalDataSource,
        remoteDataSource: PokemonRemoteDataSource
    ): PokemonDataSource = PokemonDataSourceImpl(localDataSource, remoteDataSource)
}
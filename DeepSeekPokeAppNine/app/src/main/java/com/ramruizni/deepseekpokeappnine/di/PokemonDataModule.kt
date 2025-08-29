package com.ramruizni.deepseekpokeappnine.di

import com.ramruizni.deepseekpokeappnine.pokemon.datasource.PokemonDataSource
import com.ramruizni.deepseekpokeappnine.pokemon.datasource.PokemonDataSourceImpl
import com.ramruizni.deepseekpokeappnine.pokemon.datasource.daos.PokemonDao
import com.ramruizni.deepseekpokeappnine.pokemon.datasource.remote.PokeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonDataModule {

    @Provides
    @Singleton
    fun providePokemonDataSource(
        pokemonDao: PokemonDao,
        pokeApiService: PokeApiService
    ): PokemonDataSource = PokemonDataSourceImpl(pokemonDao, pokeApiService)
}
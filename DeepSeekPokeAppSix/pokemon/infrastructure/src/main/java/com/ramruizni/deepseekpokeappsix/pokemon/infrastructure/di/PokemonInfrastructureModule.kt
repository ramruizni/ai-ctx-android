package com.ramruizni.deepseekpokeappsix.pokemon.infrastructure.di

import com.ramruizni.deepseekpokeappsix.pokemon.datasource.PokemonDataSource
import com.ramruizni.deepseekpokeappsix.pokemon.domain.PokemonRepository
import com.ramruizni.deepseekpokeappsix.pokemon.infrastructure.PokemonRepositoryImpl
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
    fun providePokemonRepository(
        pokemonDataSource: PokemonDataSource
    ): PokemonRepository {
        return PokemonRepositoryImpl(pokemonDataSource)
    }
}
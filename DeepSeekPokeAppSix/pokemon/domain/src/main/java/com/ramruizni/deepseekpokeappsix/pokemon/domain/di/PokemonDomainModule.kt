package com.ramruizni.deepseekpokeappsix.pokemon.domain.di

import com.ramruizni.deepseekpokeappsix.pokemon.domain.GetAllPokemonUseCase
import com.ramruizni.deepseekpokeappsix.pokemon.domain.GetPokemonByIdUseCase
import com.ramruizni.deepseekpokeappsix.pokemon.domain.PokemonRepository
import com.ramruizni.deepseekpokeappsix.pokemon.domain.RefreshPokemonUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonDomainModule {

    @Singleton
    @Provides
    fun provideGetAllPokemonUseCase(
        pokemonRepository: PokemonRepository
    ): GetAllPokemonUseCase {
        return GetAllPokemonUseCase(pokemonRepository)
    }

    @Singleton
    @Provides
    fun provideGetPokemonByIdUseCase(
        pokemonRepository: PokemonRepository
    ): GetPokemonByIdUseCase {
        return GetPokemonByIdUseCase(pokemonRepository)
    }

    @Singleton
    @Provides
    fun provideRefreshPokemonUseCase(
        pokemonRepository: PokemonRepository
    ): RefreshPokemonUseCase {
        return RefreshPokemonUseCase(pokemonRepository)
    }
}
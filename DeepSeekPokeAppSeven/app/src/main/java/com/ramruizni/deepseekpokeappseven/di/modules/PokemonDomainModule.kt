package com.ramruizni.deepseekpokeappseven.di.modules

import com.ramruizni.deepseekpokeappseven.pokemon.domain.GetPokemonByIdUseCase
import com.ramruizni.deepseekpokeappseven.pokemon.domain.GetPokemonListUseCase
import com.ramruizni.deepseekpokeappseven.pokemon.domain.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonDomainModule {
    
    @Provides
    @Singleton
    fun provideGetPokemonListUseCase(
        pokemonRepository: PokemonRepository
    ): GetPokemonListUseCase {
        return GetPokemonListUseCase(pokemonRepository)
    }
    
    @Provides
    @Singleton
    fun provideGetPokemonByIdUseCase(
        pokemonRepository: PokemonRepository
    ): GetPokemonByIdUseCase {
        return GetPokemonByIdUseCase(pokemonRepository)
    }
}
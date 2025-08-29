package com.ramruizni.deepseekpokeappeight.pokemon.domain.di

import com.ramruizni.deepseekpokeappeight.pokemon.domain.GetPokemonByIdUseCase
import com.ramruizni.deepseekpokeappeight.pokemon.domain.GetPokemonListUseCase
import com.ramruizni.deepseekpokeappeight.pokemon.domain.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module providing domain layer dependencies for Pokemon feature.
 * Provides use cases following manual instantiation pattern.
 */
@Module
@InstallIn(SingletonComponent::class)
object PokemonDomainModule {
    
    /**
     * Provides GetPokemonListUseCase with manual instantiation
     * @param repository The Pokemon repository implementation
     * @return GetPokemonListUseCase instance
     */
    @Provides
    @Singleton
    fun provideGetPokemonListUseCase(
        repository: PokemonRepository
    ): GetPokemonListUseCase {
        return GetPokemonListUseCase(repository)
    }
    
    /**
     * Provides GetPokemonByIdUseCase with manual instantiation
     * @param repository The Pokemon repository implementation
     * @return GetPokemonByIdUseCase instance
     */
    @Provides
    @Singleton
    fun provideGetPokemonByIdUseCase(
        repository: PokemonRepository
    ): GetPokemonByIdUseCase {
        return GetPokemonByIdUseCase(repository)
    }
}
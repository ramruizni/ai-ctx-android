package com.example.deepseekpokeappten.di.modules

import com.example.deepseekpokeappten.pokemon.domain.FetchAndSavePokemonListUseCase
import com.example.deepseekpokeappten.pokemon.domain.GetPokemonListUseCase
import com.example.deepseekpokeappten.pokemon.domain.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonUseCaseModule {

    @Provides
    @Singleton
    fun provideGetPokemonListUseCase(
        repository: PokemonRepository
    ): GetPokemonListUseCase {
        return GetPokemonListUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFetchAndSavePokemonListUseCase(
        repository: PokemonRepository
    ): FetchAndSavePokemonListUseCase {
        return FetchAndSavePokemonListUseCase(repository)
    }
}
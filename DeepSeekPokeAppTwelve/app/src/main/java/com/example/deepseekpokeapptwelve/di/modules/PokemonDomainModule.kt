package com.example.deepseekpokeapptwelve.di.modules

import com.example.deepseekpokeapptwelve.pokemon.domain.repository.PokemonRepository
import com.example.deepseekpokeapptwelve.pokemon.domain.usecases.GetAllPokemonUseCase
import com.example.deepseekpokeapptwelve.pokemon.domain.usecases.FetchPokemonListUseCase
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
    fun provideGetAllPokemonUseCase(pokemonRepository: PokemonRepository): GetAllPokemonUseCase {
        return GetAllPokemonUseCase(pokemonRepository)
    }

    @Singleton
    @Provides
    fun provideFetchPokemonListUseCase(pokemonRepository: PokemonRepository): FetchPokemonListUseCase {
        return FetchPokemonListUseCase(pokemonRepository)
    }
}
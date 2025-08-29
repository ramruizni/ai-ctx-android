package com.example.deepseekpokeappeleven.di.modules

import com.example.deepseekpokeappeleven.pokemon.domain.repositories.PokemonRepository
import com.example.deepseekpokeappeleven.pokemon.domain.usecases.GetPokemonListUseCase
import com.example.deepseekpokeappeleven.pokemon.domain.usecases.RefreshPokemonListUseCase
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
    fun providesGetPokemonListUseCase(
        repository: PokemonRepository
    ): GetPokemonListUseCase = GetPokemonListUseCase(repository)

    @Provides
    @Singleton
    fun providesRefreshPokemonListUseCase(
        repository: PokemonRepository
    ): RefreshPokemonListUseCase = RefreshPokemonListUseCase(repository)
}
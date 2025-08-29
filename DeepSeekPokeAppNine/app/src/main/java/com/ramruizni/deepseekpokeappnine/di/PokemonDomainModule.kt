package com.ramruizni.deepseekpokeappnine.di

import com.ramruizni.deepseekpokeappnine.pokemon.domain.GetPokemonListUseCase
import com.ramruizni.deepseekpokeappnine.pokemon.domain.PokemonRepository
import com.ramruizni.deepseekpokeappnine.pokemon.domain.RefreshPokemonListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PokemonDomainModule {

    @Provides
    fun provideGetPokemonListUseCase(repository: PokemonRepository): GetPokemonListUseCase =
        GetPokemonListUseCase(repository)

    @Provides
    fun provideRefreshPokemonListUseCase(repository: PokemonRepository): RefreshPokemonListUseCase =
        RefreshPokemonListUseCase(repository)
}
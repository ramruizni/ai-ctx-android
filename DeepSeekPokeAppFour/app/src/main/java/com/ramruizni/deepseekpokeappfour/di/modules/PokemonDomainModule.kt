package com.ramruizni.deepseekpokeappfour.di.modules

import com.ramruizni.deepseekpokeappfour.pokemon.domain.PokemonRepository
import com.ramruizni.deepseekpokeappfour.pokemon.domain.usecases.GetAllPokemonUseCase
import com.ramruizni.deepseekpokeappfour.pokemon.domain.usecases.GetPokemonByIdUseCase
import com.ramruizni.deepseekpokeappfour.pokemon.domain.usecases.RefreshPokemonUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PokemonDomainModule {
    
    @Provides
    fun providesGetAllPokemonUseCase(
        repository: PokemonRepository
    ): GetAllPokemonUseCase = GetAllPokemonUseCase(repository)
    
    @Provides
    fun providesGetPokemonByIdUseCase(
        repository: PokemonRepository
    ): GetPokemonByIdUseCase = GetPokemonByIdUseCase(repository)
    
    @Provides  
    fun providesRefreshPokemonUseCase(
        repository: PokemonRepository
    ): RefreshPokemonUseCase = RefreshPokemonUseCase(repository)
}
package com.ramruizni.deepseekpokeappthirteen.di.modules

import com.ramruizni.deepseekpokeappthirteen.pokemon.domain.repository.PokemonRepository
import com.ramruizni.deepseekpokeappthirteen.pokemon.domain.usecase.FetchAndStorePokemonListUseCase
import com.ramruizni.deepseekpokeappthirteen.pokemon.domain.usecase.GetAllPokemonUseCase
import com.ramruizni.deepseekpokeappthirteen.pokemon.domain.usecase.GetPokemonByIdUseCase
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
    ): GetAllPokemonUseCase = GetAllPokemonUseCase(pokemonRepository)
    
    @Singleton
    @Provides
    fun provideFetchAndStorePokemonListUseCase(
        pokemonRepository: PokemonRepository
    ): FetchAndStorePokemonListUseCase = FetchAndStorePokemonListUseCase(pokemonRepository)
    
    @Singleton
    @Provides
    fun provideGetPokemonByIdUseCase(
        pokemonRepository: PokemonRepository
    ): GetPokemonByIdUseCase = GetPokemonByIdUseCase(pokemonRepository)
}
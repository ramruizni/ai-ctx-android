package com.ramruizni.deepseekpokeappthirteen.di.modules

import com.ramruizni.deepseekpokeappthirteen.pokemon.datasource.local.PokemonDataSource
import com.ramruizni.deepseekpokeappthirteen.pokemon.datasource.remote.PokemonApiService
import com.ramruizni.deepseekpokeappthirteen.pokemon.domain.repository.PokemonRepository
import com.ramruizni.deepseekpokeappthirteen.pokemon.infrastructure.repository.PokemonRepositoryImpl
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
        pokemonDataSource: PokemonDataSource,
        pokemonApiService: PokemonApiService
    ): PokemonRepository = PokemonRepositoryImpl(pokemonDataSource, pokemonApiService)
}
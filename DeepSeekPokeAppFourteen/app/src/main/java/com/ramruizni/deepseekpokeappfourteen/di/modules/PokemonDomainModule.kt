package com.ramruizni.deepseekpokeappfourteen.di.modules

import com.ramruizni.deepseekpokeappfourteen.pokemon.domain.GetAllPokemonUseCase
import com.ramruizni.deepseekpokeappfourteen.pokemon.domain.GetPokemonByIdUseCase
import com.ramruizni.deepseekpokeappfourteen.pokemon.domain.PokemonRepository
import com.ramruizni.deepseekpokeappfourteen.pokemon.domain.RefreshPokemonUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object PokemonDomainModule {

    @Provides
    fun provideGetAllPokemonUseCase(
        repository: PokemonRepository
    ): GetAllPokemonUseCase = GetAllPokemonUseCase(repository)

    @Provides
    fun provideRefreshPokemonUseCase(
        repository: PokemonRepository
    ): RefreshPokemonUseCase = RefreshPokemonUseCase(repository)

    @Provides
    fun provideGetPokemonByIdUseCase(
        repository: PokemonRepository
    ): GetPokemonByIdUseCase = GetPokemonByIdUseCase(repository)
}
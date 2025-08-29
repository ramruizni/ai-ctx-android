package com.ramruizni.deepseekpokeappfourteen.di.modules

import com.ramruizni.deepseekpokeappfourteen.pokemon.datasource.PokemonDataSource
import com.ramruizni.deepseekpokeappfourteen.pokemon.datasource.remote.PokemonRemoteDataSource
import com.ramruizni.deepseekpokeappfourteen.pokemon.domain.PokemonRepository
import com.ramruizni.deepseekpokeappfourteen.pokemon.infrastructure.repository.PokemonRepositoryImpl
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
        localDataSource: PokemonDataSource,
        remoteDataSource: PokemonRemoteDataSource
    ): PokemonRepository = PokemonRepositoryImpl(localDataSource, remoteDataSource)
}
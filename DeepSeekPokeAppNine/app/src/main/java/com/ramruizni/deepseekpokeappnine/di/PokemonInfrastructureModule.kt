package com.ramruizni.deepseekpokeappnine.di

import com.ramruizni.deepseekpokeappnine.pokemon.datasource.PokemonDataSource
import com.ramruizni.deepseekpokeappnine.pokemon.domain.PokemonRepository
import com.ramruizni.deepseekpokeappnine.pokemon.infrastructure.PokemonRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonInfrastructureModule {

    @Provides
    @Singleton
    fun providePokemonRepository(dataSource: PokemonDataSource): PokemonRepository =
        PokemonRepositoryImpl(dataSource)
}
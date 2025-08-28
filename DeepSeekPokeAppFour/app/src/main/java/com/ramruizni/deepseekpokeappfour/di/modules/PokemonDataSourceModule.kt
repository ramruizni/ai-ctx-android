package com.ramruizni.deepseekpokeappfour.di.modules

import com.ramruizni.deepseekpokeappfour.pokemon.datasource.PokemonDao
import com.ramruizni.deepseekpokeappfour.pokemon.datasource.PokemonDataSource
import com.ramruizni.deepseekpokeappfour.pokemon.datasource.PokemonDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonDataSourceModule {
    
    @Singleton
    @Provides
    fun providesPokemonDataSource(
        dao: PokemonDao
    ): PokemonDataSource = PokemonDataSourceImpl(dao)
}
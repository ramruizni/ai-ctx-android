package com.ramruizni.deepseekpokeappfour.di.modules

import com.ramruizni.deepseekpokeappfour.pokemon.datasource.network.PokeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonNetworkModule {
    
    @Singleton
    @Provides
    fun providesPokeApiService(
        retrofit: Retrofit
    ): PokeApiService = retrofit.create(PokeApiService::class.java)
}
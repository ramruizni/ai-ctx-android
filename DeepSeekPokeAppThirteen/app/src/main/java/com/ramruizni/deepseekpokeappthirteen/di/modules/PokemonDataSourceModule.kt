package com.ramruizni.deepseekpokeappthirteen.di.modules

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ramruizni.deepseekpokeappthirteen.database.DemoDatabase
import com.ramruizni.deepseekpokeappthirteen.pokemon.datasource.daos.PokemonDao
import com.ramruizni.deepseekpokeappthirteen.pokemon.datasource.local.PokemonDataSource
import com.ramruizni.deepseekpokeappthirteen.pokemon.datasource.local.PokemonDataSourceImpl
import com.ramruizni.deepseekpokeappthirteen.pokemon.datasource.remote.PokemonApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonDataSourceModule {
    
    @Singleton
    @Provides
    fun providePokemonDataSource(
        pokemonDao: PokemonDao
    ): PokemonDataSource = PokemonDataSourceImpl(pokemonDao)
    
    @Singleton
    @Provides
    fun providePokemonDao(database: DemoDatabase): PokemonDao = 
        PokemonDaoAdapter(database.pokemonDao())
    
    @Singleton
    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
    
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
    
    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
    
    @Singleton
    @Provides
    fun providePokemonApiService(retrofit: Retrofit): PokemonApiService =
        retrofit.create(PokemonApiService::class.java)
}
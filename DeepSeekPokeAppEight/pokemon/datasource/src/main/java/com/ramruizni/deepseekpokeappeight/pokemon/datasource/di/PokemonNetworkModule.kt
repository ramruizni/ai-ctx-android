package com.ramruizni.deepseekpokeappeight.pokemon.datasource.di

import com.ramruizni.deepseekpokeappeight.pokemon.datasource.remote.PokemonApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Dagger Hilt module providing network layer dependencies for Pokemon feature.
 * Configures Retrofit, OkHttp, and JSON serialization for PokeAPI integration.
 */
@Module
@InstallIn(SingletonComponent::class)
object PokemonNetworkModule {
    
    private const val BASE_URL = "https://pokeapi.co/api/v2/"
    private const val CONNECT_TIMEOUT = 30L
    private const val READ_TIMEOUT = 30L
    private const val WRITE_TIMEOUT = 30L
    
    /**
     * Provides JSON serializer configuration for kotlinx.serialization
     * @return Configured Json instance with error handling
     */
    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
        encodeDefaults = false
    }
    
    /**
     * Provides OkHttp client with proper timeouts and logging
     * @return Configured OkHttpClient instance
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }
    
    /**
     * Provides Retrofit instance configured for PokeAPI
     * @param okHttpClient HTTP client for network requests
     * @param json JSON serializer for request/response parsing
     * @return Configured Retrofit instance
     */
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory("application/json; charset=UTF8".toMediaType())
            )
            .build()
    }
    
    /**
     * Provides PokemonApiService from Retrofit
     * @param retrofit Configured Retrofit instance
     * @return PokemonApiService implementation
     */
    @Provides
    @Singleton
    fun providePokemonApiService(retrofit: Retrofit): PokemonApiService {
        return retrofit.create(PokemonApiService::class.java)
    }
}
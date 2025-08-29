package com.ramruizni.deepseekpokeappeight.pokemon.datasource.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {
    
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 151,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse
    
    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(
        @Path("id") id: Int
    ): PokemonDetailResponse
    
    @GET("pokemon/{name}")
    suspend fun getPokemonDetailsByName(
        @Path("name") name: String
    ): PokemonDetailResponse
    
    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}
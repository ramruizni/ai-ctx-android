package com.ramruizni.deepseekpokeappseven.pokemon.datasource.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse
    
    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(@Path("id") id: Int): PokemonDetailResponse
    
    @GET("pokemon/{name}")
    suspend fun getPokemonDetailByName(@Path("name") name: String): PokemonDetailResponse
}
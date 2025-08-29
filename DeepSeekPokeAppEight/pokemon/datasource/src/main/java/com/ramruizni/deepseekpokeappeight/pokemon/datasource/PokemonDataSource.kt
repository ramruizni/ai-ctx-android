package com.ramruizni.deepseekpokeappeight.pokemon.datasource

import com.ramruizni.deepseekpokeappeight.pokemon.local.PokemonWithDetails
import com.ramruizni.deepseekpokeappeight.pokemon.datasource.remote.PokemonDetailResponse
import com.ramruizni.deepseekpokeappeight.pokemon.datasource.remote.PokemonListResponse
import kotlinx.coroutines.flow.Flow

interface PokemonDataSource {
    
    // Remote data access
    suspend fun fetchPokemonList(limit: Int = 151, offset: Int = 0): Result<PokemonListResponse>
    suspend fun fetchPokemonDetails(id: Int): Result<PokemonDetailResponse>
    suspend fun fetchPokemonDetailsByName(name: String): Result<PokemonDetailResponse>
    
    // Local data access
    fun getAllPokemonFromCache(): Flow<List<PokemonWithDetails>>
    suspend fun getPokemonFromCache(id: Int): PokemonWithDetails?
    suspend fun cachePokemonDetails(pokemonDetail: PokemonDetailResponse)
    suspend fun clearPokemonCache()
    
    // Combined operations
    suspend fun getPokemonList(
        limit: Int = 151, 
        offset: Int = 0, 
        forceRefresh: Boolean = false
    ): Flow<List<PokemonWithDetails>>
    
    suspend fun getPokemonDetails(
        id: Int, 
        forceRefresh: Boolean = false
    ): Result<PokemonWithDetails>
}
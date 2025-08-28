package com.ramruizni.deepseekpokeappfive.pokemon.domain

import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun fetchAndSavePokemonList(limit: Int = 20, offset: Int = 0): Result<Unit>
    
    fun getAllPokemon(): Flow<List<Pokemon>>
    
    fun getPokemonById(id: Int): Flow<Pokemon?>
    
    suspend fun refreshPokemonData(): Result<Unit>
    
    suspend fun clearAllPokemon(): Result<Unit>
}
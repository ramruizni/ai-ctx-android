package com.ramruizni.deepseekpokeappfour.pokemon.datasource

import kotlinx.coroutines.flow.Flow

interface PokemonDataSource {
    fun getAllPokemon(): Flow<List<PokemonDbDto>>
    suspend fun insertPokemon(pokemon: List<PokemonDbDto>)
    suspend fun insertSinglePokemon(pokemon: PokemonDbDto)
    fun getPokemonById(id: Int): Flow<PokemonDbDto?>
    suspend fun updatePokemon(pokemon: PokemonDbDto)
    suspend fun deletePokemon(pokemon: PokemonDbDto)
    suspend fun deleteAllPokemon()
    suspend fun getPokemonCount(): Int
}
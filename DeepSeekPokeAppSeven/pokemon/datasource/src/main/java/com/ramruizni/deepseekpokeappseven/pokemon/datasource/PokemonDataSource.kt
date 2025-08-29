package com.ramruizni.deepseekpokeappseven.pokemon.datasource

import com.ramruizni.deepseekpokeappseven.database.entities.PokemonDbDto
import kotlinx.coroutines.flow.Flow

interface PokemonDataSource {
    suspend fun getPokemonList(limit: Int = 20, offset: Int = 0): List<PokemonDbDto>
    suspend fun getPokemonById(id: Int): PokemonDbDto?
    suspend fun searchPokemon(query: String): List<PokemonDbDto>
    fun observePokemonList(): Flow<List<PokemonDbDto>>
    suspend fun refreshPokemonList()
}
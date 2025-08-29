package com.ramruizni.deepseekpokeappseven.pokemon.domain

import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int = 20, offset: Int = 0): List<Pokemon>
    suspend fun getPokemonById(id: Int): Pokemon?
    suspend fun searchPokemon(query: String): List<Pokemon>
    fun observePokemonList(): Flow<List<Pokemon>>
    suspend fun refreshPokemonList()
}
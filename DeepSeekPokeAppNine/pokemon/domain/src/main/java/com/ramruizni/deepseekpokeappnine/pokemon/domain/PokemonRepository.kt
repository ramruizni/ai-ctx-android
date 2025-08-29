package com.ramruizni.deepseekpokeappnine.pokemon.domain

import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): List<Pokemon>
    fun getAllPokemonFromCache(): Flow<List<Pokemon>>
    suspend fun getPokemonById(id: Int): Pokemon?
    suspend fun refreshPokemonList(limit: Int, offset: Int): List<Pokemon>
}
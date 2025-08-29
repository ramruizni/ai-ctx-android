package com.example.deepseekpokeappeleven.pokemon.domain.repositories

import com.example.deepseekpokeappeleven.pokemon.domain.models.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonList(limit: Int = 20, offset: Int = 0): Flow<List<Pokemon>>
    suspend fun refreshPokemonList(limit: Int = 20, offset: Int = 0)
    suspend fun getPokemonById(id: Int): Pokemon?
}
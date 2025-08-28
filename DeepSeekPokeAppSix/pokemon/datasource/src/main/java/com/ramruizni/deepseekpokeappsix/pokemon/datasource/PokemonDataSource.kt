package com.ramruizni.deepseekpokeappsix.pokemon.datasource

import com.ramruizni.deepseekpokeappsix.pokemon.domain.models.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonDataSource {
    suspend fun getAllPokemon(): Flow<List<Pokemon>>
    suspend fun getPokemonById(id: Int): Pokemon?
    suspend fun searchPokemon(name: String): Flow<List<Pokemon>>
    suspend fun refreshPokemon(): Result<Unit>
}
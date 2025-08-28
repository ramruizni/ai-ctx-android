package com.ramruizni.deepseekpokeappsix.pokemon.domain

import com.ramruizni.deepseekpokeappsix.pokemon.domain.models.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getAllPokemon(): Flow<List<Pokemon>>
    suspend fun getPokemonById(id: Int): Pokemon?
    suspend fun searchPokemon(name: String): Flow<List<Pokemon>>
    suspend fun refreshPokemon(): Result<Unit>
}
package com.ramruizni.deepseekpokeappfour.pokemon.domain

import com.ramruizni.deepseekpokeappfour.pokemon.domain.models.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getAllPokemon(): Flow<List<Pokemon>>
    suspend fun refreshPokemon(): Result<Unit>
    fun getPokemonById(id: Int): Flow<Pokemon?>
}
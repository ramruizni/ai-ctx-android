package com.ramruizni.deepseekpokeappfourteen.pokemon.domain

import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun getAllPokemon(offset: Int = 0, limit: Int = 20): List<Pokemon>
    fun getAllPokemonFlow(): Flow<List<Pokemon>>
    suspend fun getPokemonById(id: Int): Pokemon?
    suspend fun refreshPokemon(offset: Int = 0, limit: Int = 20)
}
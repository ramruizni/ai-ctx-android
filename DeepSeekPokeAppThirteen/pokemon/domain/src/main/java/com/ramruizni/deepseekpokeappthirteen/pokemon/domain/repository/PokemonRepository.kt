package com.ramruizni.deepseekpokeappthirteen.pokemon.domain.repository

import com.ramruizni.deepseekpokeappthirteen.pokemon.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun fetchAndStorePokemonList(limit: Int = 151): List<Pokemon>
    fun getAllPokemon(): Flow<List<Pokemon>>
    suspend fun getPokemonById(id: Int): Pokemon?
    suspend fun refreshPokemonList(): List<Pokemon>
}
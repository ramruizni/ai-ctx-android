package com.example.deepseekpokeappeleven.pokemon.datasource

import com.example.deepseekpokeappeleven.pokemon.datasource.dbdtos.PokemonDbDto
import kotlinx.coroutines.flow.Flow

interface PokemonDataSource {
    fun getPokemonList(limit: Int, offset: Int): Flow<List<PokemonDbDto>>
    suspend fun refreshPokemonList(limit: Int, offset: Int)
    suspend fun getPokemonById(id: Int): PokemonDbDto?
}
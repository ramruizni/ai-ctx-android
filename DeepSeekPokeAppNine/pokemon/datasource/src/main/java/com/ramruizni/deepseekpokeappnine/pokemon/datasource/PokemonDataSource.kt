package com.ramruizni.deepseekpokeappnine.pokemon.datasource

import com.ramruizni.deepseekpokeappnine.pokemon.datasource.dbdtos.PokemonDbDto
import kotlinx.coroutines.flow.Flow

interface PokemonDataSource {
    suspend fun getPokemonFromApi(limit: Int, offset: Int): List<PokemonDbDto>
    fun getAllPokemonFromDb(): Flow<List<PokemonDbDto>>
    suspend fun getPokemonByIdFromDb(id: Int): PokemonDbDto?
    suspend fun savePokemonToDb(pokemon: List<PokemonDbDto>)
    suspend fun clearPokemonDb()
}
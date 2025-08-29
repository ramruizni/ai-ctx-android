package com.example.deepseekpokeappeleven.pokemon.datasource.local

import com.example.deepseekpokeappeleven.pokemon.datasource.entities.PokemonEntity
import kotlinx.coroutines.flow.Flow

interface PokemonLocalDataSource {
    fun getPokemonList(limit: Int, offset: Int): Flow<List<PokemonEntity>>
    suspend fun getPokemonById(id: Int): PokemonEntity?
    suspend fun insertPokemon(pokemon: List<PokemonEntity>)
    suspend fun clearAll()
}
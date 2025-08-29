package com.example.deepseekpokeappten.pokemon.datasource

import com.example.deepseekpokeappten.database.entities.PokemonEntity
import com.example.deepseekpokeappten.pokemon.datasource.remote.PokemonDto
import com.example.deepseekpokeappten.pokemon.datasource.remote.PokemonListItemDto
import kotlinx.coroutines.flow.Flow

interface PokemonDataSource {
    // Remote data source methods
    suspend fun fetchPokemonList(limit: Int, offset: Int): List<PokemonListItemDto>
    suspend fun fetchPokemon(id: Int): PokemonDto
    suspend fun fetchPokemon(name: String): PokemonDto

    // Local data source methods
    fun getAllPokemon(): Flow<List<PokemonEntity>>
    suspend fun getPokemonById(id: Int): PokemonEntity?
    suspend fun getPokemonByName(name: String): PokemonEntity?
    suspend fun savePokemon(pokemon: PokemonEntity)
    suspend fun savePokemonList(pokemonList: List<PokemonEntity>)
    suspend fun clearAllPokemon()
}
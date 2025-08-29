package com.example.deepseekpokeappten.pokemon.domain

import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun fetchPokemonList(limit: Int = 20, offset: Int = 0): List<PokemonListItem>
    suspend fun fetchPokemon(id: Int): Pokemon
    suspend fun fetchPokemon(name: String): Pokemon
    fun getAllPokemon(): Flow<List<Pokemon>>
    suspend fun savePokemon(pokemon: Pokemon)
    suspend fun savePokemonList(pokemonList: List<Pokemon>)
}
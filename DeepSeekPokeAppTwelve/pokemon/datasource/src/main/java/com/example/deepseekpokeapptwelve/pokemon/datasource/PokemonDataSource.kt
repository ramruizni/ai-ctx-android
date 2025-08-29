package com.example.deepseekpokeapptwelve.pokemon.datasource

import com.example.deepseekpokeapptwelve.pokemon.domain.models.Pokemon
import com.example.deepseekpokeapptwelve.pokemon.domain.models.PokemonListItem
import kotlinx.coroutines.flow.Flow

interface PokemonDataSource {
    suspend fun fetchPokemonList(limit: Int, offset: Int): List<PokemonListItem>
    suspend fun fetchPokemonDetails(name: String): Pokemon
    fun getAllPokemon(): Flow<List<Pokemon>>
    suspend fun savePokemon(pokemon: Pokemon)
    suspend fun savePokemonList(pokemonList: List<Pokemon>)
    suspend fun getPokemonById(id: Int): Pokemon?
    suspend fun getPokemonByName(name: String): Pokemon?
}
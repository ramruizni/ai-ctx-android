package com.example.deepseekpokeapptwelve.pokemon.infrastructure.repository

import com.example.deepseekpokeapptwelve.pokemon.datasource.PokemonDataSource
import com.example.deepseekpokeapptwelve.pokemon.domain.repository.PokemonRepository
import com.example.deepseekpokeapptwelve.pokemon.domain.models.Pokemon
import com.example.deepseekpokeapptwelve.pokemon.domain.models.PokemonListItem
import kotlinx.coroutines.flow.Flow

class PokemonRepositoryImpl(
    private val pokemonDataSource: PokemonDataSource
) : PokemonRepository {
    
    override suspend fun fetchPokemonList(limit: Int, offset: Int): List<PokemonListItem> {
        return pokemonDataSource.fetchPokemonList(limit, offset)
    }

    override suspend fun fetchPokemonDetails(name: String): Pokemon {
        return pokemonDataSource.fetchPokemonDetails(name)
    }

    override fun getAllPokemon(): Flow<List<Pokemon>> {
        return pokemonDataSource.getAllPokemon()
    }

    override suspend fun savePokemon(pokemon: Pokemon) {
        pokemonDataSource.savePokemon(pokemon)
    }

    override suspend fun savePokemonList(pokemonList: List<Pokemon>) {
        pokemonDataSource.savePokemonList(pokemonList)
    }

    override suspend fun getPokemonById(id: Int): Pokemon? {
        return pokemonDataSource.getPokemonById(id)
    }

    override suspend fun getPokemonByName(name: String): Pokemon? {
        return pokemonDataSource.getPokemonByName(name)
    }
}
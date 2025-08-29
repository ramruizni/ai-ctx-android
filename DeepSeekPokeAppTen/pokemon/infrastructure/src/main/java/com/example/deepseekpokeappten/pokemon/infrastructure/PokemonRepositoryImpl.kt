package com.example.deepseekpokeappten.pokemon.infrastructure

import com.example.deepseekpokeappten.pokemon.datasource.PokemonDataSource
import com.example.deepseekpokeappten.pokemon.datasource.mappers.toDomain
import com.example.deepseekpokeappten.pokemon.datasource.mappers.toEntity
import com.example.deepseekpokeappten.pokemon.datasource.remote.toDomain
import com.example.deepseekpokeappten.pokemon.domain.Pokemon
import com.example.deepseekpokeappten.pokemon.domain.PokemonListItem
import com.example.deepseekpokeappten.pokemon.domain.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonRepositoryImpl(
    private val dataSource: PokemonDataSource
) : PokemonRepository {

    override suspend fun fetchPokemonList(limit: Int, offset: Int): List<PokemonListItem> {
        return dataSource.fetchPokemonList(limit, offset).map { it.toDomain() }
    }

    override suspend fun fetchPokemon(id: Int): Pokemon {
        return dataSource.fetchPokemon(id).toDomain()
    }

    override suspend fun fetchPokemon(name: String): Pokemon {
        return dataSource.fetchPokemon(name).toDomain()
    }

    override fun getAllPokemon(): Flow<List<Pokemon>> {
        return dataSource.getAllPokemon().map { pokemonList ->
            pokemonList.map { it.toDomain() }
        }
    }

    override suspend fun savePokemon(pokemon: Pokemon) {
        dataSource.savePokemon(pokemon.toEntity())
    }

    override suspend fun savePokemonList(pokemonList: List<Pokemon>) {
        dataSource.savePokemonList(pokemonList.map { it.toEntity() })
    }
}
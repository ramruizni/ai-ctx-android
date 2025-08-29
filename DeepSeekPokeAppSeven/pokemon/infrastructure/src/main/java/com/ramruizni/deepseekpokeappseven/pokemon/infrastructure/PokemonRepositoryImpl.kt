package com.ramruizni.deepseekpokeappseven.pokemon.infrastructure

import com.ramruizni.deepseekpokeappseven.pokemon.datasource.PokemonDataSource
import com.ramruizni.deepseekpokeappseven.pokemon.domain.Pokemon
import com.ramruizni.deepseekpokeappseven.pokemon.domain.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonRepositoryImpl(
    private val pokemonDataSource: PokemonDataSource
) : PokemonRepository {

    override suspend fun getPokemonList(limit: Int, offset: Int): List<Pokemon> {
        return pokemonDataSource.getPokemonList(limit, offset).map { it.toDomain() }
    }

    override suspend fun getPokemonById(id: Int): Pokemon? {
        return pokemonDataSource.getPokemonById(id)?.toDomain()
    }

    override suspend fun searchPokemon(query: String): List<Pokemon> {
        return pokemonDataSource.searchPokemon(query).map { it.toDomain() }
    }

    override fun observePokemonList(): Flow<List<Pokemon>> {
        return pokemonDataSource.observePokemonList().map { pokemonList ->
            pokemonList.map { it.toDomain() }
        }
    }

    override suspend fun refreshPokemonList() {
        pokemonDataSource.refreshPokemonList()
    }
}
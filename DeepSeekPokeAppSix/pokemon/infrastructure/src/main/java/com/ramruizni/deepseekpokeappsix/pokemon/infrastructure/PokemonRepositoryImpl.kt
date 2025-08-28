package com.ramruizni.deepseekpokeappsix.pokemon.infrastructure

import com.ramruizni.deepseekpokeappsix.pokemon.datasource.PokemonDataSource
import com.ramruizni.deepseekpokeappsix.pokemon.domain.PokemonRepository
import com.ramruizni.deepseekpokeappsix.pokemon.domain.models.Pokemon
import kotlinx.coroutines.flow.Flow

class PokemonRepositoryImpl(
    private val pokemonDataSource: PokemonDataSource
) : PokemonRepository {
    
    override suspend fun getAllPokemon(): Flow<List<Pokemon>> {
        return pokemonDataSource.getAllPokemon()
    }
    
    override suspend fun getPokemonById(id: Int): Pokemon? {
        return pokemonDataSource.getPokemonById(id)
    }
    
    override suspend fun searchPokemon(name: String): Flow<List<Pokemon>> {
        return pokemonDataSource.searchPokemon(name)
    }
    
    override suspend fun refreshPokemon(): Result<Unit> {
        return pokemonDataSource.refreshPokemon()
    }
}
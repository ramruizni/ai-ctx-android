package com.ramruizni.deepseekpokeappeight.pokemon.infrastructure

import com.ramruizni.deepseekpokeappeight.pokemon.datasource.PokemonDataSource
import com.ramruizni.deepseekpokeappeight.pokemon.domain.Pokemon
import com.ramruizni.deepseekpokeappeight.pokemon.domain.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository implementation that bridges the domain and data layers
 * Implements PokemonRepository interface defined in the domain module
 * Following Clean Architecture principles with manual instantiation pattern
 */
class PokemonRepositoryImpl(
    private val pokemonDataSource: PokemonDataSource
) : PokemonRepository {
    
    override fun observePokemonList(): Flow<List<Pokemon>> {
        return pokemonDataSource.getAllPokemonFromCache()
            .map { pokemonList ->
                pokemonList.map { pokemonWithDetails ->
                    PokemonDomainMapper.toDomain(pokemonWithDetails)
                }
            }
    }
    
    override suspend fun refreshPokemonList() {
        // Trigger refresh by fetching data with forceRefresh = true
        pokemonDataSource.getPokemonList(forceRefresh = true)
    }
    
    override suspend fun getPokemonById(id: Int): Pokemon? {
        return try {
            val result = pokemonDataSource.getPokemonDetails(id, forceRefresh = false)
            result.fold(
                onSuccess = { pokemonWithDetails ->
                    PokemonDomainMapper.toDomain(pokemonWithDetails)
                },
                onFailure = { null }
            )
        } catch (e: Exception) {
            null
        }
    }
    
    override fun searchPokemon(query: String): Flow<List<Pokemon>> {
        return observePokemonList().map { pokemonList ->
            pokemonList.filter { pokemon ->
                pokemon.name.contains(query, ignoreCase = true)
            }
        }
    }
}
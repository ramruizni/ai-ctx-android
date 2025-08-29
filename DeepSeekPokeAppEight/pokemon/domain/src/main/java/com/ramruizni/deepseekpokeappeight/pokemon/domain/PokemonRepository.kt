package com.ramruizni.deepseekpokeappeight.pokemon.domain

import kotlinx.coroutines.flow.Flow

/**
 * Repository interface defining the contract for Pokemon data access
 * Following Clean Architecture principles - pure abstraction in domain layer
 */
interface PokemonRepository {
    
    /**
     * Observes the list of Pokemon with reactive updates
     * @return Flow of Pokemon list for real-time updates
     */
    fun observePokemonList(): Flow<List<Pokemon>>
    
    /**
     * Refreshes the Pokemon list from remote source
     * Triggers data sync and updates the local cache
     */
    suspend fun refreshPokemonList()
    
    /**
     * Gets detailed information for a specific Pokemon by ID
     * @param id The Pokemon ID to fetch
     * @return Pokemon details or null if not found
     */
    suspend fun getPokemonById(id: Int): Pokemon?
    
    /**
     * Searches Pokemon by name (for future search functionality)
     * @param query Search query string
     * @return Flow of filtered Pokemon list
     */
    fun searchPokemon(query: String): Flow<List<Pokemon>>
}
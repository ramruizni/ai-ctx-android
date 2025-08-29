package com.ramruizni.deepseekpokeappeight.pokemon.domain

import kotlinx.coroutines.flow.Flow

/**
 * Use case for observing and refreshing the Pokemon list
 * Follows simple use case pattern with direct invoke method
 */
class GetPokemonListUseCase(
    private val pokemonRepository: PokemonRepository
) {
    
    /**
     * Observes the Pokemon list with reactive updates
     * @return Flow of Pokemon list
     */
    operator fun invoke(): Flow<List<Pokemon>> {
        return pokemonRepository.observePokemonList()
    }
    
    /**
     * Refreshes the Pokemon list from remote source
     * Call this to trigger data sync
     */
    suspend fun refresh() {
        pokemonRepository.refreshPokemonList()
    }
}
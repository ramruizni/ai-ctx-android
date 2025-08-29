package com.ramruizni.deepseekpokeappeight.pokemon.viewmodel

import com.ramruizni.deepseekpokeappeight.pokemon.domain.Pokemon

/**
 * UI state for the Pokemon list screen
 * Represents different states the Pokemon list can be in
 */
sealed interface PokemonListUiState {
    
    /**
     * Initial loading state when fetching Pokemon for the first time
     */
    data object Loading : PokemonListUiState
    
    /**
     * Success state with Pokemon list loaded
     * @param pokemonList List of Pokemon to display
     * @param isRefreshing Whether a refresh operation is in progress
     */
    data class Success(
        val pokemonList: List<Pokemon>,
        val isRefreshing: Boolean = false
    ) : PokemonListUiState {
        
        /**
         * Whether the Pokemon list is empty
         */
        val isEmpty: Boolean get() = pokemonList.isEmpty()
    }
    
    /**
     * Error state when loading fails
     * @param message Error message to display
     * @param canRetry Whether the user can retry the operation
     */
    data class Error(
        val message: String,
        val canRetry: Boolean = true
    ) : PokemonListUiState
}

/**
 * UI events that can be triggered from the Pokemon list screen
 */
sealed interface PokemonListUiEvent {
    
    /**
     * User wants to refresh the Pokemon list
     */
    data object Refresh : PokemonListUiEvent
    
    /**
     * User wants to retry loading after an error
     */
    data object Retry : PokemonListUiEvent
    
    /**
     * User selected a Pokemon to view details
     * @param pokemon The selected Pokemon
     */
    data class PokemonSelected(val pokemon: Pokemon) : PokemonListUiEvent
}
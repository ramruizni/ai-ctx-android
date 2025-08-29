package com.ramruizni.deepseekpokeappeight.pokemon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramruizni.deepseekpokeappeight.pokemon.domain.GetPokemonListUseCase
import com.ramruizni.deepseekpokeappeight.pokemon.domain.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Pokemon list screen
 * Manages UI state and coordinates with the domain layer
 */
@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<PokemonListUiState>(PokemonListUiState.Loading)
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()
    
    init {
        loadPokemonList()
    }
    
    /**
     * Handle UI events from the screen
     */
    fun onEvent(event: PokemonListUiEvent) {
        when (event) {
            is PokemonListUiEvent.Refresh -> refreshPokemonList()
            is PokemonListUiEvent.Retry -> retryLoadPokemonList()
            is PokemonListUiEvent.PokemonSelected -> handlePokemonSelected(event.pokemon)
        }
    }
    
    /**
     * Initial loading of Pokemon list
     */
    private fun loadPokemonList() {
        viewModelScope.launch {
            _uiState.value = PokemonListUiState.Loading
            
            getPokemonListUseCase()
                .catch { exception ->
                    _uiState.value = PokemonListUiState.Error(
                        message = exception.message ?: "Failed to load Pokemon list",
                        canRetry = true
                    )
                }
                .collect { pokemonList ->
                    _uiState.value = PokemonListUiState.Success(
                        pokemonList = pokemonList,
                        isRefreshing = false
                    )
                }
        }
    }
    
    /**
     * Refresh Pokemon list (pull-to-refresh)
     */
    private fun refreshPokemonList() {
        viewModelScope.launch {
            // Update UI to show refreshing state
            val currentState = _uiState.value
            if (currentState is PokemonListUiState.Success) {
                _uiState.value = currentState.copy(isRefreshing = true)
            }
            
            try {
                // Trigger refresh from remote source
                getPokemonListUseCase.refresh()
                
                // The flow will automatically emit updated data
                // which will be collected in loadPokemonList()
            } catch (exception: Exception) {
                // If refresh fails but we have cached data, show error as snackbar
                // Otherwise show full error state
                if (currentState is PokemonListUiState.Success) {
                    _uiState.value = currentState.copy(isRefreshing = false)
                    // TODO: Show snackbar error message
                } else {
                    _uiState.value = PokemonListUiState.Error(
                        message = exception.message ?: "Failed to refresh Pokemon list",
                        canRetry = true
                    )
                }
            }
        }
    }
    
    /**
     * Retry loading Pokemon list after error
     */
    private fun retryLoadPokemonList() {
        loadPokemonList()
    }
    
    /**
     * Handle Pokemon selection (for future detail navigation)
     */
    private fun handlePokemonSelected(pokemon: Pokemon) {
        // TODO: Navigate to Pokemon detail screen
        // This will be implemented when navigation is set up
    }
}
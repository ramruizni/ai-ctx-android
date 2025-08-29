package com.ramruizni.deepseekpokeappfourteen.features.pokemon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramruizni.deepseekpokeappfourteen.pokemon.domain.GetAllPokemonUseCase
import com.ramruizni.deepseekpokeappfourteen.pokemon.domain.RefreshPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getAllPokemonUseCase: GetAllPokemonUseCase,
    private val refreshPokemonUseCase: RefreshPokemonUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonListUiState())
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()

    init {
        loadPokemon()
    }

    fun loadPokemon() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            // Start collecting Pokemon data from local database
            getAllPokemonUseCase()
                .catch { throwable ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Failed to load Pokemon: ${throwable.message}"
                    )
                }
                .collect { pokemonList ->
                    _uiState.value = _uiState.value.copy(
                        pokemonList = pokemonList,
                        isLoading = false,
                        error = null
                    )
                }
        }
    }

    fun refreshPokemon() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isRefreshing = true, error = null)
                
                // Refresh data from API
                refreshPokemonUseCase(offset = 0, limit = 50)
                
                _uiState.value = _uiState.value.copy(isRefreshing = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isRefreshing = false,
                    error = "Failed to refresh Pokemon: ${e.message}"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
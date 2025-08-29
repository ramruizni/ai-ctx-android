package com.example.deepseekpokeappeleven.features.pokemon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deepseekpokeappeleven.pokemon.domain.usecases.GetPokemonListUseCase
import com.example.deepseekpokeappeleven.pokemon.domain.usecases.RefreshPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val refreshPokemonListUseCase: RefreshPokemonListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonListUiState())
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()

    init {
        loadPokemonList()
        refreshPokemonList()
    }

    fun refreshPokemonList() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isRefreshing = true, errorMessage = null)
                refreshPokemonListUseCase(limit = 50, offset = 0)
                _uiState.value = _uiState.value.copy(isRefreshing = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isRefreshing = false,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    private fun loadPokemonList() {
        viewModelScope.launch {
            getPokemonListUseCase(limit = 50, offset = 0)
                .onStart {
                    _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
                }
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Failed to load Pokemon list"
                    )
                }
                .collect { pokemonList ->
                    _uiState.value = _uiState.value.copy(
                        pokemons = pokemonList,
                        isLoading = false,
                        errorMessage = null
                    )
                }
        }
    }
}
package com.ramruizni.deepseekpokeappnine.features.pokemon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramruizni.deepseekpokeappnine.pokemon.domain.GetPokemonListUseCase
import com.ramruizni.deepseekpokeappnine.pokemon.domain.RefreshPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val refreshPokemonListUseCase: RefreshPokemonListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonListUiState())
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()

    fun loadPokemonList() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            getPokemonListUseCase()
                .catch { throwable ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = throwable.message ?: "Failed to load Pokemon list"
                    )
                }
                .collect { pokemonList ->
                    if (pokemonList.isEmpty()) {
                        // If cache is empty, fetch from API
                        refreshPokemonList(isInitialLoad = true)
                    } else {
                        _uiState.value = _uiState.value.copy(
                            pokemonList = pokemonList,
                            isLoading = false,
                            error = null
                        )
                    }
                }
        }
    }

    fun refreshPokemonList(isInitialLoad: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isRefreshing = !isInitialLoad,
                isLoading = isInitialLoad,
                error = null
            )
            
            try {
                val pokemonList = refreshPokemonListUseCase()
                _uiState.value = _uiState.value.copy(
                    pokemonList = pokemonList,
                    isLoading = false,
                    isRefreshing = false,
                    error = null
                )
            } catch (throwable: Throwable) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isRefreshing = false,
                    error = throwable.message ?: "Failed to refresh Pokemon list"
                )
            }
        }
    }
}
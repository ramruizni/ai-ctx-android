package com.ramruizni.deepseekpokeappseven.features.pokemon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramruizni.deepseekpokeappseven.pokemon.domain.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonListUiState())
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)

    init {
        observePokemonList()
        refreshPokemonList()
    }

    fun refreshPokemonList() {
        viewModelScope.launch {
            try {
                _isRefreshing.value = true
                _errorMessage.value = null
                getPokemonListUseCase.refreshPokemonList()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to refresh Pokemon list: ${e.localizedMessage}"
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    private fun observePokemonList() {
        viewModelScope.launch {
            combine(
                getPokemonListUseCase.observePokemonList(),
                _isRefreshing,
                _errorMessage
            ) { pokemonList, isRefreshing, errorMessage ->
                PokemonListUiState(
                    pokemonList = pokemonList,
                    isLoading = isRefreshing && pokemonList.isEmpty(),
                    isRefreshing = isRefreshing,
                    errorMessage = errorMessage
                )
            }.catch { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isRefreshing = false,
                    errorMessage = "Failed to load Pokemon list: ${exception.localizedMessage}"
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
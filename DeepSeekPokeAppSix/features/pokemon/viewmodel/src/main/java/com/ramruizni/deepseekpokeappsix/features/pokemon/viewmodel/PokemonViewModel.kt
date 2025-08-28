package com.ramruizni.deepseekpokeappsix.features.pokemon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramruizni.deepseekpokeappsix.pokemon.domain.GetAllPokemonUseCase
import com.ramruizni.deepseekpokeappsix.pokemon.domain.models.Pokemon
import com.ramruizni.deepseekpokeappsix.pokemon.domain.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getAllPokemon: GetAllPokemonUseCase,
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonUiState())
    val uiState = _uiState.asStateFlow()

    private val _pokemon = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemon = _pokemon.asStateFlow()

    private val _events = Channel<PokemonEvent>()
    val events = _events.receiveAsFlow()

    init {
        loadPokemon()
    }

    private fun loadPokemon() {
        viewModelScope.launch {
            getAllPokemon()
                .onStart {
                    _uiState.value = _uiState.value.copy(
                        isLoading = true,
                        error = null
                    )
                }
                .catch { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "An unknown error occurred"
                    )
                }
                .collect { pokemonList ->
                    _pokemon.value = pokemonList
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = null
                    )
                }
        }
    }

    fun refreshPokemon() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true, error = null)
            
            pokemonRepository.refreshPokemon()
                .onSuccess {
                    // The existing flow from getAllPokemon will automatically update
                    _uiState.value = _uiState.value.copy(isRefreshing = false)
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isRefreshing = false,
                        error = exception.message ?: "Failed to refresh Pokemon"
                    )
                }
        }
    }

    fun retryLoadPokemon() {
        loadPokemon()
    }

    fun onPokemonClick(pokemonId: Int) {
        viewModelScope.launch {
            _events.send(PokemonEvent.NavigateToPokemonDetail(pokemonId))
        }
    }
}
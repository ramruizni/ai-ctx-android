package com.example.deepseekpokeapptwelve.features.pokemon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deepseekpokeapptwelve.pokemon.domain.models.Pokemon
import com.example.deepseekpokeapptwelve.pokemon.domain.usecases.GetAllPokemonUseCase
import com.example.deepseekpokeapptwelve.pokemon.domain.usecases.FetchPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PokemonListState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val pokemon: List<Pokemon> = emptyList(),
    val isRefreshing: Boolean = false
)

sealed class PokemonListEvent {
    data class ShowError(val message: String) : PokemonListEvent()
    data class ShowSuccess(val message: String) : PokemonListEvent()
}

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getAllPokemon: GetAllPokemonUseCase,
    private val fetchPokemonList: FetchPokemonListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PokemonListState())
    val state = _state.asStateFlow()

    private val _events = Channel<PokemonListEvent>()
    val events = _events.receiveAsFlow()

    init {
        loadPokemon()
        observePokemonFromDatabase()
    }

    private fun observePokemonFromDatabase() {
        viewModelScope.launch {
            getAllPokemon().collectLatest { pokemonList ->
                _state.update { it.copy(pokemon = pokemonList) }
            }
        }
    }

    fun loadPokemon() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true, error = null) }
            
            try {
                fetchPokemonList(limit = 20, offset = 0)
                _state.update { it.copy(isLoading = false) }
                _events.send(PokemonListEvent.ShowSuccess("Pokemon loaded successfully!"))
            } catch (e: Exception) {
                _state.update { 
                    it.copy(
                        isLoading = false, 
                        error = e.message ?: "Unknown error occurred"
                    ) 
                }
                _events.send(PokemonListEvent.ShowError(e.message ?: "Failed to load Pokemon"))
            }
        }
    }

    fun refreshPokemon() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isRefreshing = true, error = null) }
            
            try {
                fetchPokemonList(limit = 20, offset = 0)
                _state.update { it.copy(isRefreshing = false) }
                _events.send(PokemonListEvent.ShowSuccess("Pokemon refreshed!"))
            } catch (e: Exception) {
                _state.update { 
                    it.copy(
                        isRefreshing = false, 
                        error = e.message ?: "Unknown error occurred"
                    ) 
                }
                _events.send(PokemonListEvent.ShowError(e.message ?: "Failed to refresh Pokemon"))
            }
        }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }
}
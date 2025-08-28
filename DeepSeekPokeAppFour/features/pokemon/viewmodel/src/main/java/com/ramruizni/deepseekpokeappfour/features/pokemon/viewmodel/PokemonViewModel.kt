package com.ramruizni.deepseekpokeappfour.features.pokemon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    // TODO: Inject use cases when domain layer is available
    // private val getAllPokemonUseCase: GetAllPokemonUseCase,
    // private val refreshPokemonUseCase: RefreshPokemonUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonUiState())
    val uiState: StateFlow<PokemonUiState> = _uiState.asStateFlow()

    init {
        loadPokemon()
    }

    fun loadPokemon() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            try {
                // TODO: Replace with actual use case when domain layer is available
                val mockPokemon = getMockPokemon()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    pokemon = mockPokemon
                )
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = exception.message ?: "An unknown error occurred"
                )
            }
        }
    }

    fun refreshPokemon() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true, errorMessage = null)
            
            try {
                // TODO: Replace with actual refresh use case when domain layer is available
                val mockPokemon = getMockPokemon()
                _uiState.value = _uiState.value.copy(
                    isRefreshing = false,
                    pokemon = mockPokemon
                )
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isRefreshing = false,
                    errorMessage = exception.message ?: "Failed to refresh Pokemon"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    // TODO: Remove when actual domain layer is available
    private fun getMockPokemon(): List<Pokemon> {
        return listOf(
            Pokemon(
                id = 1,
                name = "Bulbasaur",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                types = listOf(
                    PokemonType("grass", PokemonType.getTypeColor("grass")),
                    PokemonType("poison", PokemonType.getTypeColor("poison"))
                )
            ),
            Pokemon(
                id = 4,
                name = "Charmander",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png",
                types = listOf(
                    PokemonType("fire", PokemonType.getTypeColor("fire"))
                )
            ),
            Pokemon(
                id = 7,
                name = "Squirtle",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png",
                types = listOf(
                    PokemonType("water", PokemonType.getTypeColor("water"))
                )
            ),
            Pokemon(
                id = 25,
                name = "Pikachu",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png",
                types = listOf(
                    PokemonType("electric", PokemonType.getTypeColor("electric"))
                )
            ),
            Pokemon(
                id = 150,
                name = "Mewtwo",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/150.png",
                types = listOf(
                    PokemonType("psychic", PokemonType.getTypeColor("psychic"))
                )
            ),
            Pokemon(
                id = 151,
                name = "Mew",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/151.png",
                types = listOf(
                    PokemonType("psychic", PokemonType.getTypeColor("psychic"))
                )
            )
        )
    }
}
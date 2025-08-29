package com.ramruizni.deepseekpokeappthirteen.features.pokemon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramruizni.deepseekpokeappthirteen.pokemon.domain.usecase.FetchAndStorePokemonListUseCase
import com.ramruizni.deepseekpokeappthirteen.pokemon.domain.usecase.GetAllPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getAllPokemonUseCase: GetAllPokemonUseCase,
    private val fetchAndStorePokemonListUseCase: FetchAndStorePokemonListUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(PokemonListUiState())
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()
    
    init {
        observePokemonList()
        loadPokemonList()
    }
    
    private fun observePokemonList() {
        getAllPokemonUseCase()
            .onEach { pokemonList ->
                _uiState.value = _uiState.value.copy(
                    pokemon = pokemonList,
                    isLoading = false,
                    isError = false
                )
            }
            .launchIn(viewModelScope)
    }
    
    private fun loadPokemonList() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, isError = false)
                fetchAndStorePokemonListUseCase()
                // The observePokemonList will automatically update the UI
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "An unknown error occurred"
                )
            }
        }
    }
    
    fun refreshPokemonList() {
        loadPokemonList()
    }
}
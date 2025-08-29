package com.example.deepseekpokeappten.features.pokemon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deepseekpokeappten.pokemon.domain.FetchAndSavePokemonListUseCase
import com.example.deepseekpokeappten.pokemon.domain.GetPokemonListUseCase
import com.example.deepseekpokeappten.pokemon.domain.Pokemon
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
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val fetchAndSavePokemonListUseCase: FetchAndSavePokemonListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonListUiState())
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    private val _isRefreshing = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)

    init {
        observePokemonList()
        loadPokemonList()
    }

    private fun observePokemonList() {
        viewModelScope.launch {
            combine(
                getPokemonListUseCase(),
                _isLoading,
                _isRefreshing,
                _errorMessage
            ) { pokemonList, isLoading, isRefreshing, errorMessage ->
                PokemonListUiState(
                    pokemonList = pokemonList.map { it.toUiModel() },
                    isLoading = isLoading,
                    isRefreshing = isRefreshing,
                    errorMessage = errorMessage
                )
            }.catch { throwable ->
                _errorMessage.value = throwable.message ?: "Unknown error occurred"
                _isLoading.value = false
                _isRefreshing.value = false
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun loadPokemonList() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                fetchAndSavePokemonListUseCase(limit = 50)
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Failed to load Pokemon list"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshPokemonList() {
        viewModelScope.launch {
            _isRefreshing.value = true
            _errorMessage.value = null
            try {
                fetchAndSavePokemonListUseCase(limit = 50)
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Failed to refresh Pokemon list"
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    private fun Pokemon.toUiModel(): PokemonUiModel = PokemonUiModel(
        id = id,
        name = name,
        imageUrl = sprites.frontDefault,
        number = "#${id.toString().padStart(3, '0')}",
        types = types.map { it.type.name.replaceFirstChar { char -> char.uppercase() } }
    )
}
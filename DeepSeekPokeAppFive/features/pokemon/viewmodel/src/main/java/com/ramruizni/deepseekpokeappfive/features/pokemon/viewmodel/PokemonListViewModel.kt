package com.ramruizni.deepseekpokeappfive.features.pokemon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramruizni.deepseekpokeappfive.pokemon.domain.Pokemon
import com.ramruizni.deepseekpokeappfive.pokemon.domain.usecase.FetchPokemonListUseCase
import com.ramruizni.deepseekpokeappfive.pokemon.domain.usecase.GetAllPokemonUseCase
import com.ramruizni.deepseekpokeappfive.pokemon.domain.usecase.RefreshPokemonDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getAllPokemonUseCase: GetAllPokemonUseCase,
    private val fetchPokemonListUseCase: FetchPokemonListUseCase,
    private val refreshPokemonDataUseCase: RefreshPokemonDataUseCase
) : ViewModel() {
    
    val uiState: StateFlow<PokemonListUiState> = getAllPokemonUseCase()
        .map { pokemonList ->
            if (pokemonList.isEmpty()) {
                PokemonListUiState.Empty
            } else {
                PokemonListUiState.Success(pokemonList)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PokemonListUiState.Loading
        )
    
    init {
        loadInitialData()
    }
    
    private fun loadInitialData() {
        viewModelScope.launch {
            val result = fetchPokemonListUseCase(limit = 20)
            if (result.isFailure) {
                // Handle error - UI state will show empty state
            }
        }
    }
    
    fun refreshData() {
        viewModelScope.launch {
            refreshPokemonDataUseCase()
        }
    }
    
    fun loadMorePokemon() {
        viewModelScope.launch {
            val currentCount = (uiState.value as? PokemonListUiState.Success)?.pokemonList?.size ?: 0
            fetchPokemonListUseCase(limit = 20, offset = currentCount)
        }
    }
}

sealed interface PokemonListUiState {
    data object Loading : PokemonListUiState
    data object Empty : PokemonListUiState
    data class Success(val pokemonList: List<Pokemon>) : PokemonListUiState
}
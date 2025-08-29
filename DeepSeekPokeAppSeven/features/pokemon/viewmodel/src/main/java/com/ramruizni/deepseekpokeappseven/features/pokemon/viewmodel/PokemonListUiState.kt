package com.ramruizni.deepseekpokeappseven.features.pokemon.viewmodel

import com.ramruizni.deepseekpokeappseven.pokemon.domain.Pokemon

data class PokemonListUiState(
    val pokemonList: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false
)
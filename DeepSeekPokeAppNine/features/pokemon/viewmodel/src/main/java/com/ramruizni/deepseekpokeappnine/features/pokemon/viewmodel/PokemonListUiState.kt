package com.ramruizni.deepseekpokeappnine.features.pokemon.viewmodel

import com.ramruizni.deepseekpokeappnine.pokemon.domain.Pokemon

data class PokemonListUiState(
    val pokemonList: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null
)
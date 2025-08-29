package com.ramruizni.deepseekpokeappfourteen.features.pokemon.viewmodel

import com.ramruizni.deepseekpokeappfourteen.pokemon.domain.Pokemon

data class PokemonListUiState(
    val pokemonList: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null
)
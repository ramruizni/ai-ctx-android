package com.example.deepseekpokeappeleven.features.pokemon.viewmodel

import com.example.deepseekpokeappeleven.pokemon.domain.models.Pokemon

data class PokemonListUiState(
    val pokemons: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false
)
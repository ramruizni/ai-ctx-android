package com.ramruizni.deepseekpokeappsix.features.pokemon.viewmodel

data class PokemonUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null
)

sealed class PokemonEvent {
    data class NavigateToPokemonDetail(val pokemonId: Int) : PokemonEvent()
}
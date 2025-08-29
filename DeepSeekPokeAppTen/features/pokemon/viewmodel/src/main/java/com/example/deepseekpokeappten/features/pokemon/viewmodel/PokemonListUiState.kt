package com.example.deepseekpokeappten.features.pokemon.viewmodel

data class PokemonListUiState(
    val pokemonList: List<PokemonUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false
)

data class PokemonUiModel(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val number: String,
    val types: List<String>
) {
    val displayName: String = name.replaceFirstChar { it.uppercase() }
}
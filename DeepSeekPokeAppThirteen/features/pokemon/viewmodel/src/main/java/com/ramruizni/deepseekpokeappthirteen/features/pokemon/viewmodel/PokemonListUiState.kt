package com.ramruizni.deepseekpokeappthirteen.features.pokemon.viewmodel

import com.ramruizni.deepseekpokeappthirteen.pokemon.domain.model.Pokemon

data class PokemonListUiState(
    val isLoading: Boolean = false,
    val pokemon: List<Pokemon> = emptyList(),
    val isError: Boolean = false,
    val errorMessage: String = ""
)
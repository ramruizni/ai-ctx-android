package com.ramruizni.deepseekpokeappeight.pokemon.datasource.remote

import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponse(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<PokemonApiDto>
)
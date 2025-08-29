package com.ramruizni.deepseekpokeappeight.pokemon.datasource.remote

import kotlinx.serialization.Serializable

@Serializable
data class PokemonApiDto(
    val name: String,
    val url: String
)
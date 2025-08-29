package com.ramruizni.deepseekpokeappeight.pokemon.datasource.remote

import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val base_experience: Int? = null,
    val sprites: SpritesApiDto,
    val types: List<TypeApiDto>,
    val abilities: List<AbilityApiDto>,
    val stats: List<StatApiDto>
)
package com.ramruizni.deepseekpokeappeight.pokemon.datasource.remote

import kotlinx.serialization.Serializable

@Serializable
data class SpritesApiDto(
    val front_default: String? = null,
    val front_shiny: String? = null,
    val back_default: String? = null,
    val back_shiny: String? = null
)
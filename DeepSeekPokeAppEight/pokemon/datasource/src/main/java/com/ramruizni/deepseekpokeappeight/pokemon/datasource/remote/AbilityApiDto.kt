package com.ramruizni.deepseekpokeappeight.pokemon.datasource.remote

import kotlinx.serialization.Serializable

@Serializable
data class AbilityApiDto(
    val slot: Int,
    val ability: AbilityDetailApiDto,
    val is_hidden: Boolean
)

@Serializable
data class AbilityDetailApiDto(
    val name: String,
    val url: String
)
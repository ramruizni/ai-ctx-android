package com.ramruizni.deepseekpokeappeight.pokemon.datasource.remote

import kotlinx.serialization.Serializable

@Serializable
data class StatApiDto(
    val base_stat: Int,
    val effort: Int,
    val stat: StatDetailApiDto
)

@Serializable
data class StatDetailApiDto(
    val name: String,
    val url: String
)
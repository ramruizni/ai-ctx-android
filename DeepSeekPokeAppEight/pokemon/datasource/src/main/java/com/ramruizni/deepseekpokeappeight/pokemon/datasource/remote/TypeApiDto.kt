package com.ramruizni.deepseekpokeappeight.pokemon.datasource.remote

import kotlinx.serialization.Serializable

@Serializable
data class TypeApiDto(
    val slot: Int,
    val type: TypeDetailApiDto
)

@Serializable
data class TypeDetailApiDto(
    val name: String,
    val url: String
)
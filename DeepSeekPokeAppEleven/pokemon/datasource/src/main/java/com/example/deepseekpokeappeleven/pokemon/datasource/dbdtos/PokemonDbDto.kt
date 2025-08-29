package com.example.deepseekpokeappeleven.pokemon.datasource.dbdtos

import kotlinx.serialization.Serializable

@Serializable
data class PokemonDbDto(
    val id: Int,
    val name: String,
    val number: Int,
    val imageUrl: String,
    val types: List<PokemonTypeDbDto>,
    val height: Int,
    val weight: Int,
    val abilities: List<PokemonAbilityDbDto>,
    val stats: List<PokemonStatDbDto>
)

@Serializable
data class PokemonTypeDbDto(
    val name: String,
    val color: String? = null
)

@Serializable
data class PokemonAbilityDbDto(
    val name: String,
    val isHidden: Boolean = false
)

@Serializable
data class PokemonStatDbDto(
    val name: String,
    val baseStat: Int,
    val effort: Int = 0
)
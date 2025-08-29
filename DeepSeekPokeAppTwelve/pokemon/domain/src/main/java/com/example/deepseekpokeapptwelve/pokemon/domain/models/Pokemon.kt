package com.example.deepseekpokeapptwelve.pokemon.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val baseExperience: Int,
    val types: List<PokemonType>,
    val abilities: List<PokemonAbility>,
    val stats: List<PokemonStat>,
    val sprites: PokemonSprites
)

@Serializable
data class PokemonType(
    val slot: Int,
    val name: String,
    val url: String
)

@Serializable
data class PokemonAbility(
    val slot: Int,
    val name: String,
    val url: String,
    val isHidden: Boolean
)

@Serializable
data class PokemonStat(
    val name: String,
    val url: String,
    val baseStat: Int,
    val effort: Int
)

@Serializable
data class PokemonSprites(
    val frontDefault: String?,
    val frontShiny: String?,
    val backDefault: String?,
    val backShiny: String?
)

@Serializable
data class PokemonListItem(
    val name: String,
    val url: String
)
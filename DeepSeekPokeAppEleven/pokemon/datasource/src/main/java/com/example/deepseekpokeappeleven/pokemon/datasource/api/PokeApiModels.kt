package com.example.deepseekpokeappeleven.pokemon.datasource.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonBasic>
)

@Serializable
data class PokemonBasic(
    val name: String,
    val url: String
) {
    val id: Int get() = url.split("/").dropLast(1).last().toInt()
}

@Serializable
data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: PokemonSprites,
    val types: List<PokemonTypeSlot>,
    val abilities: List<PokemonAbilitySlot>,
    val stats: List<PokemonStatSlot>
)

@Serializable
data class PokemonSprites(
    @SerialName("front_default")
    val frontDefault: String?
)

@Serializable
data class PokemonTypeSlot(
    val slot: Int,
    val type: PokemonTypeInfo
)

@Serializable
data class PokemonTypeInfo(
    val name: String,
    val url: String
)

@Serializable
data class PokemonAbilitySlot(
    @SerialName("is_hidden")
    val isHidden: Boolean,
    val slot: Int,
    val ability: PokemonAbilityInfo
)

@Serializable
data class PokemonAbilityInfo(
    val name: String,
    val url: String
)

@Serializable
data class PokemonStatSlot(
    @SerialName("base_stat")
    val baseStat: Int,
    val effort: Int,
    val stat: PokemonStatInfo
)

@Serializable
data class PokemonStatInfo(
    val name: String,
    val url: String
)
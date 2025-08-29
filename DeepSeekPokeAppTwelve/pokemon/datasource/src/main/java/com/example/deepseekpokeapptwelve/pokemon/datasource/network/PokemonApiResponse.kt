package com.example.deepseekpokeapptwelve.pokemon.datasource.network

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PokemonListResponse(
    @SerialName("count") val count: Int,
    @SerialName("next") val next: String?,
    @SerialName("previous") val previous: String?,
    @SerialName("results") val results: List<PokemonListItemDto>
)

@Serializable
data class PokemonListItemDto(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String
)

@Serializable
data class PokemonNetworkDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("height") val height: Int,
    @SerialName("weight") val weight: Int,
    @SerialName("base_experience") val baseExperience: Int,
    @SerialName("types") val types: List<PokemonTypeDto>,
    @SerialName("abilities") val abilities: List<PokemonAbilityDto>,
    @SerialName("stats") val stats: List<PokemonStatDto>,
    @SerialName("sprites") val sprites: PokemonSpritesDto
)

@Serializable
data class PokemonTypeDto(
    @SerialName("slot") val slot: Int,
    @SerialName("type") val type: TypeInfoDto
)

@Serializable
data class TypeInfoDto(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String
)

@Serializable
data class PokemonAbilityDto(
    @SerialName("slot") val slot: Int,
    @SerialName("ability") val ability: AbilityInfoDto,
    @SerialName("is_hidden") val isHidden: Boolean
)

@Serializable
data class AbilityInfoDto(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String
)

@Serializable
data class PokemonStatDto(
    @SerialName("stat") val stat: StatInfoDto,
    @SerialName("base_stat") val baseStat: Int,
    @SerialName("effort") val effort: Int
)

@Serializable
data class StatInfoDto(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String
)

@Serializable
data class PokemonSpritesDto(
    @SerialName("front_default") val frontDefault: String?,
    @SerialName("front_shiny") val frontShiny: String?,
    @SerialName("back_default") val backDefault: String?,
    @SerialName("back_shiny") val backShiny: String?
)
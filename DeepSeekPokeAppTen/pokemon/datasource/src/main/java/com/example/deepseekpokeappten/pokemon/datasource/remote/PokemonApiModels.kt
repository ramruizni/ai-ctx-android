package com.example.deepseekpokeappten.pokemon.datasource.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponseDto(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<PokemonListItemDto>
)

@Serializable
data class PokemonListItemDto(
    val name: String,
    val url: String
)

@Serializable
data class PokemonDto(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<PokemonTypeDto>,
    val sprites: PokemonSpritesDto,
    val abilities: List<PokemonAbilityDto>,
    val stats: List<PokemonStatDto>
)

@Serializable
data class PokemonTypeDto(
    val slot: Int,
    val type: NamedApiResourceDto
)

@Serializable
data class NamedApiResourceDto(
    val name: String,
    val url: String
)

@Serializable
data class PokemonSpritesDto(
    @SerialName("front_default")
    val frontDefault: String? = null,
    @SerialName("front_shiny")
    val frontShiny: String? = null,
    @SerialName("front_female")
    val frontFemale: String? = null,
    @SerialName("front_shiny_female")
    val frontShinyFemale: String? = null,
    @SerialName("back_default")
    val backDefault: String? = null,
    @SerialName("back_shiny")
    val backShiny: String? = null,
    @SerialName("back_female")
    val backFemale: String? = null,
    @SerialName("back_shiny_female")
    val backShinyFemale: String? = null
)

@Serializable
data class PokemonAbilityDto(
    @SerialName("is_hidden")
    val isHidden: Boolean,
    val slot: Int,
    val ability: NamedApiResourceDto
)

@Serializable
data class PokemonStatDto(
    @SerialName("base_stat")
    val baseStat: Int,
    val effort: Int,
    val stat: NamedApiResourceDto
)
package com.ramruizni.deepseekpokeappfour.pokemon.datasource.network

import kotlinx.serialization.Serializable

/**
 * Network DTOs for PokeAPI integration
 * These models match the PokeAPI response format
 * Documentation: https://pokeapi.co/docs/v2
 */

@Serializable
data class PokemonApiDto(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val base_experience: Int?,
    val order: Int,
    val sprites: PokemonSpritesDto,
    val types: List<PokemonTypeSlotDto>,
    val abilities: List<PokemonAbilitySlotDto>,
    val stats: List<PokemonStatSlotDto>
)

@Serializable
data class PokemonSpritesDto(
    val front_default: String?,
    val front_shiny: String?,
    val back_default: String?,
    val back_shiny: String?,
    val other: PokemonOtherSpritesDto?
)

@Serializable
data class PokemonOtherSpritesDto(
    val official_artwork: PokemonOfficialArtworkDto?
)

@Serializable
data class PokemonOfficialArtworkDto(
    val front_default: String?
)

@Serializable
data class PokemonTypeSlotDto(
    val slot: Int,
    val type: PokemonTypeDto
)

@Serializable
data class PokemonTypeDto(
    val name: String,
    val url: String
)

@Serializable
data class PokemonAbilitySlotDto(
    val slot: Int,
    val is_hidden: Boolean,
    val ability: PokemonAbilityDto
)

@Serializable
data class PokemonAbilityDto(
    val name: String,
    val url: String
)

@Serializable
data class PokemonStatSlotDto(
    val base_stat: Int,
    val effort: Int,
    val stat: PokemonStatDto
)

@Serializable
data class PokemonStatDto(
    val name: String,
    val url: String
)

/**
 * Response for the Pokemon list endpoint
 */
@Serializable
data class PokemonListResponseDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonBasicDto>
)

@Serializable
data class PokemonBasicDto(
    val name: String,
    val url: String
)
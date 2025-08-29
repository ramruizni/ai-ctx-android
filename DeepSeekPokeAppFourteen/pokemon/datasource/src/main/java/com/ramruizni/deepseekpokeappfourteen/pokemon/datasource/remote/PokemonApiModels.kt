package com.ramruizni.deepseekpokeappfourteen.pokemon.datasource.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonListItem>
)

@Serializable
data class PokemonListItem(
    val name: String,
    val url: String
) {
    fun getId(): Int {
        return url.split("/").dropLast(1).last().toInt()
    }
}

@Serializable
data class PokemonDetailsResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: PokemonSprites,
    val types: List<PokemonTypeResponse>,
    val abilities: List<PokemonAbilityResponse>,
    val stats: List<PokemonStatResponse>
)

@Serializable
data class PokemonSprites(
    @SerialName("front_default")
    val frontDefault: String?
)

@Serializable
data class PokemonTypeResponse(
    val slot: Int,
    val type: PokemonTypeInfo
)

@Serializable
data class PokemonTypeInfo(
    val name: String,
    val url: String
)

@Serializable
data class PokemonAbilityResponse(
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
data class PokemonStatResponse(
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
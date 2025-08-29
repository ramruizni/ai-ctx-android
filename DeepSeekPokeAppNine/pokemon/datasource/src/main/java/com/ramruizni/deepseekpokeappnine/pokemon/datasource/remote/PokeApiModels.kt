package com.ramruizni.deepseekpokeappnine.pokemon.datasource.remote

import com.google.gson.annotations.SerializedName

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonBasic>
)

data class PokemonBasic(
    val name: String,
    val url: String
)

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

data class PokemonSprites(
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("other")
    val other: PokemonSpritesOther?
)

data class PokemonSpritesOther(
    @SerializedName("official-artwork")
    val officialArtwork: PokemonOfficialArtwork?
)

data class PokemonOfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String?
)

data class PokemonTypeSlot(
    val slot: Int,
    val type: PokemonType
)

data class PokemonType(
    val name: String,
    val url: String
)

data class PokemonAbilitySlot(
    val slot: Int,
    val ability: PokemonAbility,
    @SerializedName("is_hidden")
    val isHidden: Boolean
)

data class PokemonAbility(
    val name: String,
    val url: String
)

data class PokemonStatSlot(
    @SerializedName("base_stat")
    val baseStat: Int,
    val effort: Int,
    val stat: PokemonStatInfo
)

data class PokemonStatInfo(
    val name: String,
    val url: String
)
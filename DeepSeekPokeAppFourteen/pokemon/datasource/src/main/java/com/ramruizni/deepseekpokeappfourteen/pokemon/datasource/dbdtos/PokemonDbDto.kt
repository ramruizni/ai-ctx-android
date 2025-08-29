package com.ramruizni.deepseekpokeappfourteen.pokemon.datasource.dbdtos

data class PokemonDbDto(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val typesJson: String,
    val abilitiesJson: String,
    val statsJson: String,
    val height: Int,
    val weight: Int
)

data class PokemonTypeDbDto(
    val name: String,
    val url: String
)

data class PokemonAbilityDbDto(
    val name: String,
    val url: String,
    val isHidden: Boolean
)

data class PokemonStatDbDto(
    val name: String,
    val baseStat: Int,
    val effort: Int,
    val url: String
)
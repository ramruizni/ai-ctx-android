package com.ramruizni.deepseekpokeappfourteen.pokemon.domain

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<PokemonType>,
    val abilities: List<PokemonAbility>,
    val stats: List<PokemonStat>,
    val height: Int,
    val weight: Int
)

data class PokemonType(
    val name: String,
    val url: String
)

data class PokemonAbility(
    val name: String,
    val url: String,
    val isHidden: Boolean
)

data class PokemonStat(
    val name: String,
    val baseStat: Int,
    val effort: Int,
    val url: String
)
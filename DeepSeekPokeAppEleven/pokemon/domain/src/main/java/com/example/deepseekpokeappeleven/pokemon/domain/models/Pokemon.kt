package com.example.deepseekpokeappeleven.pokemon.domain.models

data class Pokemon(
    val id: Int,
    val name: String,
    val number: Int,
    val imageUrl: String,
    val types: List<PokemonType>,
    val height: Int,
    val weight: Int,
    val abilities: List<PokemonAbility>,
    val stats: List<PokemonStat>
)

data class PokemonType(
    val name: String,
    val color: String? = null
)

data class PokemonAbility(
    val name: String,
    val isHidden: Boolean = false
)

data class PokemonStat(
    val name: String,
    val baseStat: Int,
    val effort: Int = 0
)
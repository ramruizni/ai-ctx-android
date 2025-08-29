package com.ramruizni.deepseekpokeappnine.pokemon.domain

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val abilities: List<String>,
    val stats: List<PokemonStat>,
    val height: Int,
    val weight: Int
)

data class PokemonStat(
    val name: String,
    val baseStat: Int,
    val effort: Int
)
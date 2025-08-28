package com.ramruizni.deepseekpokeappfour.pokemon.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val id: Int,
    val name: String,
    val number: Int,
    val imageUrl: String,
    val types: List<String>,
    val abilities: List<String>,
    val stats: PokemonStats
)

@Serializable
data class PokemonStats(
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int
)
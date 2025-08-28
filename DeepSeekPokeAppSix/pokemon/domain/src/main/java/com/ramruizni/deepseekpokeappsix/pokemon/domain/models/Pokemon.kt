package com.ramruizni.deepseekpokeappsix.pokemon.domain.models

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val height: Int,
    val weight: Int,
    val abilities: List<String>,
    val stats: Map<String, Int>
)
package com.ramruizni.deepseekpokeappthirteen.pokemon.datasource.dbdtos

data class PokemonDbDto(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val height: Int,
    val weight: Int,
    val baseExperience: Int,
    val types: String, // JSON string
    val abilities: String, // JSON string
    val stats: String // JSON string
)
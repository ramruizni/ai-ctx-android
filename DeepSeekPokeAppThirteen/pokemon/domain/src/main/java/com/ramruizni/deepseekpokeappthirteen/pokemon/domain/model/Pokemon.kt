package com.ramruizni.deepseekpokeappthirteen.pokemon.domain.model

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val height: Int,
    val weight: Int,
    val baseExperience: Int,
    val types: List<PokemonType>,
    val abilities: List<PokemonAbility>,
    val stats: List<PokemonStat>
)

data class PokemonType(
    val name: String,
    val slot: Int
)

data class PokemonAbility(
    val name: String,
    val isHidden: Boolean,
    val slot: Int
)

data class PokemonStat(
    val name: String,
    val baseStat: Int,
    val effort: Int
)
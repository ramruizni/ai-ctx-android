package com.ramruizni.deepseekpokeappfive.pokemon.domain

data class Pokemon(
    val id: Int,
    val name: String,
    val number: Int,
    val imageUrl: String,
    val types: List<PokemonType>,
    val abilities: List<Ability>,
    val stats: List<Stat>
) {
    val displayNumber: String get() = "#${number.toString().padStart(3, '0')}"
    
    data class Ability(
        val name: String,
        val isHidden: Boolean
    )
    
    data class Stat(
        val name: String,
        val baseValue: Int
    )
}

enum class PokemonType {
    NORMAL, FIRE, WATER, ELECTRIC, GRASS, ICE, FIGHTING, POISON, GROUND,
    FLYING, PSYCHIC, BUG, ROCK, GHOST, DRAGON, DARK, STEEL, FAIRY
}
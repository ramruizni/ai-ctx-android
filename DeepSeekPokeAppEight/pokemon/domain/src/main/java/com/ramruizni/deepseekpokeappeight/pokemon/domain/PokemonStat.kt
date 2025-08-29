package com.ramruizni.deepseekpokeappeight.pokemon.domain

/**
 * Pokemon stat domain model for future features (HP, Attack, Defense, etc.)
 */
data class PokemonStat(
    val name: String,
    val baseStat: Int,
    val effort: Int = 0
) {
    /**
     * Returns the formatted stat name for display
     */
    val displayName: String
        get() = when (name.lowercase()) {
            "hp" -> "HP"
            "attack" -> "Attack"
            "defense" -> "Defense"
            "special-attack" -> "Sp. Attack"
            "special-defense" -> "Sp. Defense"
            "speed" -> "Speed"
            else -> name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
}
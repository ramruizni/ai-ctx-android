package com.ramruizni.deepseekpokeappeight.pokemon.domain

/**
 * Pokemon type domain model (e.g., Fire, Water, Grass)
 */
data class PokemonType(
    val name: String,
    val url: String? = null
) {
    /**
     * Returns the capitalized type name for display
     */
    val displayName: String
        get() = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}
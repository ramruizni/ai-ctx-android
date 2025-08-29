package com.ramruizni.deepseekpokeappeight.pokemon.domain

/**
 * Pokemon ability domain model for future features
 */
data class PokemonAbility(
    val name: String,
    val url: String? = null,
    val isHidden: Boolean = false
) {
    /**
     * Returns the capitalized ability name for display
     */
    val displayName: String
        get() = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}
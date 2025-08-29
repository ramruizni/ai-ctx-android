package com.ramruizni.deepseekpokeappeight.pokemon.domain

/**
 * Main Pokemon domain model containing all the essential data for display and future use
 */
data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<PokemonType>,
    val abilities: List<PokemonAbility> = emptyList(),
    val stats: List<PokemonStat> = emptyList()
) {
    /**
     * Returns the formatted Pokemon number (e.g., "#001")
     */
    val formattedNumber: String
        get() = "#${id.toString().padStart(3, '0')}"
    
    /**
     * Returns the capitalized Pokemon name for display
     */
    val displayName: String
        get() = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}
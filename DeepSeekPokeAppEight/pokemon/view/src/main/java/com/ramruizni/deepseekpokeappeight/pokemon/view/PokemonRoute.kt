package com.ramruizni.deepseekpokeappeight.pokemon.view

import kotlinx.serialization.Serializable

/**
 * Pokemon navigation routes using type-safe navigation with kotlinx.serialization
 */
object PokemonRoute {
    
    /**
     * Route for the Pokemon list screen
     */
    @Serializable
    data object PokemonList
    
    /**
     * Route for Pokemon detail screen (for future implementation)
     * @param pokemonId ID of the Pokemon to display
     */
    @Serializable
    data class PokemonDetail(val pokemonId: Int)
}
package com.ramruizni.deepseekpokeappfour.navigation.pokemon.routes

/**
 * Route constants for Pokemon feature navigation
 */
object PokemonRoutes {
    const val POKEMON_LIST = "pokemon_list"
    const val POKEMON_DETAIL = "pokemon_detail/{pokemonId}"
    
    fun pokemonDetail(pokemonId: Int): String = "pokemon_detail/$pokemonId"
}
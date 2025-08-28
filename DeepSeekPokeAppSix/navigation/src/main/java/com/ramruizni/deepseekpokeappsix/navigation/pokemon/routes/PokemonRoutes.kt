package com.ramruizni.deepseekpokeappsix.navigation.pokemon.routes

import kotlinx.serialization.Serializable

@Serializable
data object PokemonGraphRoute

@Serializable
data object PokemonListRoute

@Serializable
data class PokemonDetailRoute(val pokemonId: Int)
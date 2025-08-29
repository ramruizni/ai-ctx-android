package com.example.deepseekpokeapptwelve.navigation.pokemon.routes

import kotlinx.serialization.Serializable

@Serializable
data object PokemonGraphRoute

@Serializable
data object PokemonListRoute

@Serializable
data class PokemonDetailRoute(val pokemonId: Int)
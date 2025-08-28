package com.ramruizni.deepseekpokeappsix.features.pokemon.view

interface IPokemonNavigator {
    fun navigateUp()
    fun navigateToPokemonDetail(pokemonId: Int)
}
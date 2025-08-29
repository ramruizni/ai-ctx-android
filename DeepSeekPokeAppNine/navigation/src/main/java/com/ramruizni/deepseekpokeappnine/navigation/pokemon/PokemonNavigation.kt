package com.ramruizni.deepseekpokeappnine.navigation.pokemon

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ramruizni.deepseekpokeappnine.features.pokemon.view.PokemonListScreen

fun NavGraphBuilder.pokemonNavigation() {
    composable<PokemonListRoute> {
        PokemonListScreen()
    }
}
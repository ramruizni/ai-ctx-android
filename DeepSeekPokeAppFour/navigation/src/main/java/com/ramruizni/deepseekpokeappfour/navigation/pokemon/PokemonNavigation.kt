package com.ramruizni.deepseekpokeappfour.navigation.pokemon

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ramruizni.deepseekpokeappfour.features.pokemon.view.PokemonScreen
import com.ramruizni.deepseekpokeappfour.navigation.pokemon.routes.PokemonRoutes

/**
 * Navigation composable for the Pokemon list screen
 */
@Composable
fun PokemonListRoute() {
    PokemonScreen()
}

/**
 * Extension function to add Pokemon navigation to the NavGraphBuilder
 */
fun NavGraphBuilder.pokemonNavigation() {
    composable(route = PokemonRoutes.POKEMON_LIST) {
        PokemonListRoute()
    }
    
    // TODO: Add Pokemon detail navigation when detail screen is implemented
    // composable(
    //     route = PokemonRoutes.POKEMON_DETAIL,
    //     arguments = listOf(navArgument("pokemonId") { type = NavType.IntType })
    // ) { backStackEntry ->
    //     val pokemonId = backStackEntry.arguments?.getInt("pokemonId") ?: 0
    //     PokemonDetailRoute(pokemonId = pokemonId)
    // }
}
package com.ramruizni.deepseekpokeappeight.pokemon.view

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

/**
 * Pokemon navigation graph
 * Contains all Pokemon-related screen destinations
 */
fun NavGraphBuilder.pokemonGraph(
    navController: NavController
) {
    navigation<PokemonGraphRoute>(
        startDestination = PokemonRoute.PokemonList
    ) {
        // Pokemon List Screen
        composable<PokemonRoute.PokemonList> {
            PokemonListScreen()
        }
        
        // Pokemon Detail Screen (for future implementation)
        composable<PokemonRoute.PokemonDetail> { backStackEntry ->
            // TODO: Implement Pokemon detail screen
            // val pokemonId = backStackEntry.arguments?.getInt("pokemonId") ?: 0
            // PokemonDetailScreen(pokemonId = pokemonId)
        }
    }
}

/**
 * Pokemon graph route for navigation
 */
@kotlinx.serialization.Serializable
data object PokemonGraphRoute
package com.ramruizni.deepseekpokeappsix.navigation.pokemon

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.ramruizni.deepseekpokeappsix.features.pokemon.view.PokemonListScreen
import com.ramruizni.deepseekpokeappsix.navigation.pokemon.routes.PokemonDetailRoute
import com.ramruizni.deepseekpokeappsix.navigation.pokemon.routes.PokemonGraphRoute
import com.ramruizni.deepseekpokeappsix.navigation.pokemon.routes.PokemonListRoute

fun NavGraphBuilder.pokemonGraph(
    navController: NavController
) {
    val navigator = PokemonNavigator(navController)

    navigation<PokemonGraphRoute>(startDestination = PokemonListRoute) {
        composable<PokemonListRoute> {
            PokemonListScreen(navigator = navigator)
        }
        
        // Placeholder for Pokemon detail screen
        composable<PokemonDetailRoute> { backStackEntry ->
            val pokemonDetailRoute = backStackEntry.toRoute<PokemonDetailRoute>()
            // TODO: Implement PokemonDetailScreen(pokemonId = pokemonDetailRoute.pokemonId, navigator = navigator)
            // For now, just show a placeholder
            Text("Pokemon Detail: ${pokemonDetailRoute.pokemonId}")
        }
    }
}
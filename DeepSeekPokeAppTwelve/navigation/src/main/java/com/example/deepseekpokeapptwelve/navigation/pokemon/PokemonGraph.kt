package com.example.deepseekpokeapptwelve.navigation.pokemon

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.deepseekpokeapptwelve.features.pokemon.view.PokemonListScreen
import com.example.deepseekpokeapptwelve.navigation.pokemon.routes.PokemonDetailRoute
import com.example.deepseekpokeapptwelve.navigation.pokemon.routes.PokemonGraphRoute
import com.example.deepseekpokeapptwelve.navigation.pokemon.routes.PokemonListRoute

fun NavGraphBuilder.pokemonGraph(navController: NavController) {
    navigation<PokemonGraphRoute>(
        startDestination = PokemonListRoute
    ) {
        composable<PokemonListRoute> {
            PokemonListScreen(
                navigator = PokemonNavigator(navController)
            )
        }
        
        composable<PokemonDetailRoute> { backStackEntry ->
            // TODO: Add Pokemon detail screen when implemented
        }
    }
}
package com.ramruizni.deepseekpokeappeight.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
// // import com.ramruizni.deepseekpokeappeight.pokemon.view.PokemonGraphRoute
// // import com.ramruizni.deepseekpokeappeight.pokemon.view.pokemonGraph

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    // TODO: Uncomment when Pokemon modules are built
    /*
    NavHost(
        route = RootGraphRoute::class,
        navController = navController,
        startDestination = RootGraphRoute
    ) {
        // pokemonGraph(navController = navController)
    }
    */
}
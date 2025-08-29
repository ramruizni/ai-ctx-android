package com.example.deepseekpokeapptwelve.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.deepseekpokeapptwelve.navigation.pokemon.pokemonGraph
import com.example.deepseekpokeapptwelve.navigation.pokemon.routes.PokemonGraphRoute

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    NavHost(
        route = RootGraphRoute::class,
        navController = navController,
        startDestination = PokemonGraphRoute
    ) {
        pokemonGraph(navController = navController)
    }
}
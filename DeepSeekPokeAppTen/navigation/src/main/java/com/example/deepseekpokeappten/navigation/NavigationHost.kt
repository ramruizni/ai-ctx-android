package com.example.deepseekpokeappten.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.deepseekpokeappten.navigation.pokemon.PokemonGraphRoute
import com.example.deepseekpokeappten.navigation.pokemon.pokemonGraph

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
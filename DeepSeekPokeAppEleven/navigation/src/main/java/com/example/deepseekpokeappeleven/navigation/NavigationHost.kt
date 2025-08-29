package com.example.deepseekpokeappeleven.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.deepseekpokeappeleven.navigation.pokemon.graphs.pokemonGraph
import com.example.deepseekpokeappeleven.navigation.pokemon.routes.PokemonGraphRoute
//import com.example.deepseekpokeappeleven.navigation.demo.demoGraph
//import com.example.deepseekpokeappeleven.navigation.demo.routes.DemoGraphRoute

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    NavHost(
        route = RootGraphRoute::class,
        navController = navController,
        startDestination = PokemonGraphRoute
    ) {
        pokemonGraph(navController = navController)
        // NAVIGATION GRAPHS GO HERE
//        demoGraph(navController = navController)
    }
}
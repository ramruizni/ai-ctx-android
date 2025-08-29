package com.ramruizni.deepseekpokeappthirteen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ramruizni.deepseekpokeappthirteen.navigation.pokemon.graph.pokemonGraph
import com.ramruizni.deepseekpokeappthirteen.navigation.pokemon.routes.PokemonGraphRoute

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
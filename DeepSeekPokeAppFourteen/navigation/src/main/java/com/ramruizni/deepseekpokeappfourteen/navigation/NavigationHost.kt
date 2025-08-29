package com.ramruizni.deepseekpokeappfourteen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ramruizni.deepseekpokeappfourteen.navigation.pokemon.pokemonGraph
import com.ramruizni.deepseekpokeappfourteen.navigation.pokemon.routes.PokemonGraphRoute
//import com.ramruizni.deepseekpokeappfourteen.navigation.demo.demoGraph
//import com.ramruizni.deepseekpokeappfourteen.navigation.demo.routes.DemoGraphRoute

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
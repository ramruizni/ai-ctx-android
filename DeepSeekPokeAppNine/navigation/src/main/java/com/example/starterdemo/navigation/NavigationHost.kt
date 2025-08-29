package com.ramruizni.deepseekpokeappnine.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ramruizni.deepseekpokeappnine.navigation.pokemon.PokemonListRoute
import com.ramruizni.deepseekpokeappnine.navigation.pokemon.pokemonNavigation

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    NavHost(
        route = RootGraphRoute::class,
        navController = navController,
        startDestination = PokemonListRoute
    ) {
        pokemonNavigation()
    }
}
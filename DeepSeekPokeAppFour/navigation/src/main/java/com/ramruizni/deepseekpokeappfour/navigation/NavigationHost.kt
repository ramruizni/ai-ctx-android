package com.ramruizni.deepseekpokeappfour.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ramruizni.deepseekpokeappfour.navigation.pokemon.pokemonNavigation
import com.ramruizni.deepseekpokeappfour.navigation.pokemon.routes.PokemonRoutes

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = PokemonRoutes.POKEMON_LIST
    ) {
        pokemonNavigation()
    }
}
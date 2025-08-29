package com.example.deepseekpokeappeleven.navigation.pokemon.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.deepseekpokeappeleven.features.pokemon.view.PokemonListScreen
import com.example.deepseekpokeappeleven.navigation.pokemon.routes.PokemonGraphRoute
import com.example.deepseekpokeappeleven.navigation.pokemon.routes.PokemonListRoute

fun NavGraphBuilder.pokemonGraph(navController: NavController) {
    navigation<PokemonGraphRoute>(
        startDestination = PokemonListRoute
    ) {
        composable<PokemonListRoute> {
            PokemonListScreen()
        }
    }
}
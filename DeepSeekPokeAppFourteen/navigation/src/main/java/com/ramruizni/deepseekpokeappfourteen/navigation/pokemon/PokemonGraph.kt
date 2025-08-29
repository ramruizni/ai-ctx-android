package com.ramruizni.deepseekpokeappfourteen.navigation.pokemon

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ramruizni.deepseekpokeappfourteen.features.pokemon.view.PokemonListScreen
import com.ramruizni.deepseekpokeappfourteen.navigation.pokemon.routes.PokemonGraphRoute
import com.ramruizni.deepseekpokeappfourteen.navigation.pokemon.routes.PokemonListRoute

fun NavGraphBuilder.pokemonGraph(navController: NavController) {
    navigation<PokemonGraphRoute>(
        startDestination = PokemonListRoute
    ) {
        composable<PokemonListRoute> {
            PokemonListScreen()
        }
    }
}
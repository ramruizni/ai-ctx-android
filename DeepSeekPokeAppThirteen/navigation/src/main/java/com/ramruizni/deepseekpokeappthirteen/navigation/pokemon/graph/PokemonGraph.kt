package com.ramruizni.deepseekpokeappthirteen.navigation.pokemon.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ramruizni.deepseekpokeappthirteen.features.pokemon.view.PokemonListScreen
import com.ramruizni.deepseekpokeappthirteen.navigation.pokemon.routes.PokemonGraphRoute
import com.ramruizni.deepseekpokeappthirteen.navigation.pokemon.routes.PokemonListRoute

fun NavGraphBuilder.pokemonGraph(navController: NavController) {
    navigation<PokemonGraphRoute>(
        startDestination = PokemonListRoute
    ) {
        composable<PokemonListRoute> {
            PokemonListScreen()
        }
    }
}
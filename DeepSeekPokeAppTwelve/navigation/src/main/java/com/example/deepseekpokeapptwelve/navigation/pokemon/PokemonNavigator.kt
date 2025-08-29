package com.example.deepseekpokeapptwelve.navigation.pokemon

import androidx.navigation.NavController
import com.example.deepseekpokeapptwelve.features.pokemon.view.IPokemonListNavigator
import com.example.deepseekpokeapptwelve.navigation.pokemon.routes.PokemonDetailRoute

class PokemonNavigator(
    private val navController: NavController
) : IPokemonListNavigator {
    
    override fun navigateToPokemonDetail(pokemonId: Int) {
        navController.navigate(PokemonDetailRoute(pokemonId))
    }
}
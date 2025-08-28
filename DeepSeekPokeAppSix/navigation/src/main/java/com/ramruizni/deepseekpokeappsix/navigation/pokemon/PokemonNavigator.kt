package com.ramruizni.deepseekpokeappsix.navigation.pokemon

import androidx.navigation.NavController
import com.ramruizni.deepseekpokeappsix.features.pokemon.view.IPokemonNavigator
import com.ramruizni.deepseekpokeappsix.navigation.pokemon.routes.PokemonDetailRoute

class PokemonNavigator(
    private val navController: NavController
) : IPokemonNavigator {
    
    override fun navigateUp() {
        navController.navigateUp()
    }
    
    override fun navigateToPokemonDetail(pokemonId: Int) {
        navController.navigate(PokemonDetailRoute(pokemonId))
    }
}
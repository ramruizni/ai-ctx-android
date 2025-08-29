package com.ramruizni.deepseekpokeappeight.pokemon.view

import androidx.navigation.NavController
import com.ramruizni.deepseekpokeappeight.pokemon.domain.Pokemon

/**
 * Navigator interface for Pokemon feature navigation
 * Provides type-safe navigation methods for Pokemon screens
 */
interface PokemonNavigator {
    
    /**
     * Navigate to the Pokemon list screen
     */
    fun navigateToPokemonList()
    
    /**
     * Navigate to Pokemon detail screen
     * @param pokemon Pokemon to show details for
     */
    fun navigateToPokemonDetail(pokemon: Pokemon)
    
    /**
     * Navigate back from current screen
     */
    fun navigateBack()
}

/**
 * Implementation of PokemonNavigator using NavController
 */
class PokemonNavigatorImpl(
    private val navController: NavController
) : PokemonNavigator {
    
    override fun navigateToPokemonList() {
        navController.navigate(PokemonRoute.PokemonList) {
            // Clear back stack to make this the root
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }
    
    override fun navigateToPokemonDetail(pokemon: Pokemon) {
        navController.navigate(
            PokemonRoute.PokemonDetail(pokemonId = pokemon.id)
        )
    }
    
    override fun navigateBack() {
        navController.popBackStack()
    }
}
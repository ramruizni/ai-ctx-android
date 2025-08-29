package com.example.deepseekpokeapptwelve.pokemon.domain.usecases

import com.example.deepseekpokeapptwelve.pokemon.domain.repository.PokemonRepository
import com.example.deepseekpokeapptwelve.pokemon.domain.models.Pokemon
import com.example.deepseekpokeapptwelve.pokemon.domain.models.PokemonListItem

class FetchPokemonListUseCase(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(limit: Int = 20, offset: Int = 0): List<Pokemon> {
        val pokemonListItems = pokemonRepository.fetchPokemonList(limit, offset)
        val pokemonDetails = mutableListOf<Pokemon>()
        
        pokemonListItems.forEach { listItem ->
            try {
                val pokemon = pokemonRepository.fetchPokemonDetails(listItem.name)
                pokemonDetails.add(pokemon)
                pokemonRepository.savePokemon(pokemon)
            } catch (e: Exception) {
                // Log error but continue processing other pokemon
            }
        }
        
        return pokemonDetails
    }
}
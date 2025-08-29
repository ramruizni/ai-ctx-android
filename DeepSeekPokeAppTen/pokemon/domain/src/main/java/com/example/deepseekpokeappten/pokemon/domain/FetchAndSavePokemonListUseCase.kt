package com.example.deepseekpokeappten.pokemon.domain

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class FetchAndSavePokemonListUseCase(private val repository: PokemonRepository) {
    suspend operator fun invoke(limit: Int = 20, offset: Int = 0) {
        coroutineScope {
            val pokemonList = repository.fetchPokemonList(limit, offset)
            
            val pokemonDetails = pokemonList.map { item ->
                async { repository.fetchPokemon(item.id) }
            }
            
            val fullPokemonList = pokemonDetails.map { it.await() }
            repository.savePokemonList(fullPokemonList)
        }
    }
}
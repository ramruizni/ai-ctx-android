package com.ramruizni.deepseekpokeappseven.pokemon.domain

import kotlinx.coroutines.flow.Flow

class GetPokemonListUseCase(
    private val pokemonRepository: PokemonRepository
) {
    fun observePokemonList(): Flow<List<Pokemon>> = pokemonRepository.observePokemonList()
    
    suspend fun refreshPokemonList() = pokemonRepository.refreshPokemonList()
}
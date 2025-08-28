package com.ramruizni.deepseekpokeappsix.pokemon.domain

import com.ramruizni.deepseekpokeappsix.pokemon.domain.models.Pokemon
import kotlinx.coroutines.flow.Flow

class GetAllPokemonUseCase(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(): Flow<List<Pokemon>> {
        return pokemonRepository.getAllPokemon()
    }
}
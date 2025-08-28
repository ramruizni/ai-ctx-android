package com.ramruizni.deepseekpokeappsix.pokemon.domain

import com.ramruizni.deepseekpokeappsix.pokemon.domain.models.Pokemon

class GetPokemonByIdUseCase(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(id: Int): Pokemon? {
        return pokemonRepository.getPokemonById(id)
    }
}
package com.ramruizni.deepseekpokeappfour.pokemon.domain.usecases

import com.ramruizni.deepseekpokeappfour.pokemon.domain.PokemonRepository
import com.ramruizni.deepseekpokeappfour.pokemon.domain.models.Pokemon
import kotlinx.coroutines.flow.Flow

class GetPokemonByIdUseCase(
    private val pokemonRepository: PokemonRepository
) {
    operator fun invoke(id: Int): Flow<Pokemon?> = pokemonRepository.getPokemonById(id)
}
package com.ramruizni.deepseekpokeappfour.pokemon.domain.usecases

import com.ramruizni.deepseekpokeappfour.pokemon.domain.PokemonRepository
import com.ramruizni.deepseekpokeappfour.pokemon.domain.models.Pokemon
import kotlinx.coroutines.flow.Flow

class GetAllPokemonUseCase(
    private val pokemonRepository: PokemonRepository
) {
    operator fun invoke(): Flow<List<Pokemon>> = pokemonRepository.getAllPokemon()
}
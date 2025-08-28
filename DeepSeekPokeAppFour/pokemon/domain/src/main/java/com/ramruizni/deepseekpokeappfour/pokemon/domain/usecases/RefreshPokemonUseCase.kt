package com.ramruizni.deepseekpokeappfour.pokemon.domain.usecases

import com.ramruizni.deepseekpokeappfour.pokemon.domain.PokemonRepository

class RefreshPokemonUseCase(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(): Result<Unit> = pokemonRepository.refreshPokemon()
}
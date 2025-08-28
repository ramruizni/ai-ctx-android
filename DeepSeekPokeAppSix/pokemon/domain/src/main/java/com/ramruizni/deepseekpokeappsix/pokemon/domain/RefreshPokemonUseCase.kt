package com.ramruizni.deepseekpokeappsix.pokemon.domain

class RefreshPokemonUseCase(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return pokemonRepository.refreshPokemon()
    }
}
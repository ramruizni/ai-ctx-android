package com.ramruizni.deepseekpokeappseven.pokemon.domain

class GetPokemonByIdUseCase(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(id: Int): Pokemon? = pokemonRepository.getPokemonById(id)
}
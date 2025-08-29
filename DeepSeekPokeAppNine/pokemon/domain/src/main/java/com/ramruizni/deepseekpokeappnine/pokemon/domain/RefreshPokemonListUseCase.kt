package com.ramruizni.deepseekpokeappnine.pokemon.domain

class RefreshPokemonListUseCase(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(limit: Int = 150, offset: Int = 0): List<Pokemon> {
        return repository.refreshPokemonList(limit, offset)
    }
}
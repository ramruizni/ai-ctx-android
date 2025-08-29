package com.example.deepseekpokeappeleven.pokemon.domain.usecases

import com.example.deepseekpokeappeleven.pokemon.domain.repositories.PokemonRepository

class RefreshPokemonListUseCase(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(limit: Int = 20, offset: Int = 0) {
        repository.refreshPokemonList(limit, offset)
    }
}
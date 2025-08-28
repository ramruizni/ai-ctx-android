package com.ramruizni.deepseekpokeappfive.pokemon.domain.usecase

import com.ramruizni.deepseekpokeappfive.pokemon.domain.PokemonRepository
import javax.inject.Inject

class FetchPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(limit: Int = 20, offset: Int = 0): Result<Unit> {
        return repository.fetchAndSavePokemonList(limit, offset)
    }
}
package com.ramruizni.deepseekpokeappfive.pokemon.domain.usecase

import com.ramruizni.deepseekpokeappfive.pokemon.domain.PokemonRepository
import javax.inject.Inject

class RefreshPokemonDataUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.refreshPokemonData()
    }
}
package com.ramruizni.deepseekpokeappfive.pokemon.domain.usecase

import com.ramruizni.deepseekpokeappfive.pokemon.domain.Pokemon
import com.ramruizni.deepseekpokeappfive.pokemon.domain.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonByIdUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(id: Int): Flow<Pokemon?> = repository.getPokemonById(id)
}
package com.ramruizni.deepseekpokeappnine.pokemon.domain

import kotlinx.coroutines.flow.Flow

class GetPokemonListUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(): Flow<List<Pokemon>> = repository.getAllPokemonFromCache()
}
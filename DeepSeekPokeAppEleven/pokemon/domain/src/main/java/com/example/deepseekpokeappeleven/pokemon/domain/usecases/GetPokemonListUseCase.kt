package com.example.deepseekpokeappeleven.pokemon.domain.usecases

import com.example.deepseekpokeappeleven.pokemon.domain.models.Pokemon
import com.example.deepseekpokeappeleven.pokemon.domain.repositories.PokemonRepository
import kotlinx.coroutines.flow.Flow

class GetPokemonListUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(limit: Int = 20, offset: Int = 0): Flow<List<Pokemon>> {
        return repository.getPokemonList(limit, offset)
    }
}
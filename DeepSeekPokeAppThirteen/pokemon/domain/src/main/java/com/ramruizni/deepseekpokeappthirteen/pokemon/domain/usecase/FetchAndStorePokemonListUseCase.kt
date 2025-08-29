package com.ramruizni.deepseekpokeappthirteen.pokemon.domain.usecase

import com.ramruizni.deepseekpokeappthirteen.pokemon.domain.model.Pokemon
import com.ramruizni.deepseekpokeappthirteen.pokemon.domain.repository.PokemonRepository

class FetchAndStorePokemonListUseCase(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(limit: Int = 151): List<Pokemon> {
        return pokemonRepository.fetchAndStorePokemonList(limit)
    }
}
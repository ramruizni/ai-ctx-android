package com.ramruizni.deepseekpokeappthirteen.pokemon.domain.usecase

import com.ramruizni.deepseekpokeappthirteen.pokemon.domain.model.Pokemon
import com.ramruizni.deepseekpokeappthirteen.pokemon.domain.repository.PokemonRepository

class GetPokemonByIdUseCase(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(id: Int): Pokemon? {
        return pokemonRepository.getPokemonById(id)
    }
}
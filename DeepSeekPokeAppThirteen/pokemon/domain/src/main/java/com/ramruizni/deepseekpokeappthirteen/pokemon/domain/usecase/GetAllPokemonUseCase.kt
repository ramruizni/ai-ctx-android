package com.ramruizni.deepseekpokeappthirteen.pokemon.domain.usecase

import com.ramruizni.deepseekpokeappthirteen.pokemon.domain.model.Pokemon
import com.ramruizni.deepseekpokeappthirteen.pokemon.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class GetAllPokemonUseCase(
    private val pokemonRepository: PokemonRepository
) {
    operator fun invoke(): Flow<List<Pokemon>> {
        return pokemonRepository.getAllPokemon()
    }
}
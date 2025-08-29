package com.ramruizni.deepseekpokeappfourteen.pokemon.domain

import kotlinx.coroutines.flow.Flow

class GetAllPokemonUseCase(private val pokemonRepository: PokemonRepository) {
    operator fun invoke(): Flow<List<Pokemon>> = pokemonRepository.getAllPokemonFlow()
}

class RefreshPokemonUseCase(private val pokemonRepository: PokemonRepository) {
    suspend operator fun invoke(offset: Int = 0, limit: Int = 20) {
        pokemonRepository.refreshPokemon(offset, limit)
    }
}

class GetPokemonByIdUseCase(private val pokemonRepository: PokemonRepository) {
    suspend operator fun invoke(id: Int): Pokemon? = pokemonRepository.getPokemonById(id)
}
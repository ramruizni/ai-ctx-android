package com.example.deepseekpokeapptwelve.pokemon.domain.usecases

import com.example.deepseekpokeapptwelve.pokemon.domain.repository.PokemonRepository
import com.example.deepseekpokeapptwelve.pokemon.domain.models.Pokemon
import kotlinx.coroutines.flow.Flow

class GetAllPokemonUseCase(
    private val pokemonRepository: PokemonRepository
) {
    operator fun invoke(): Flow<List<Pokemon>> {
        return pokemonRepository.getAllPokemon()
    }
}
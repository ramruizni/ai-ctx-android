package com.ramruizni.deepseekpokeappeight.pokemon.domain

/**
 * Use case for getting individual Pokemon details by ID
 * Follows simple use case pattern with direct invoke method
 */
class GetPokemonByIdUseCase(
    private val pokemonRepository: PokemonRepository
) {
    
    /**
     * Gets detailed Pokemon information by ID
     * @param id The Pokemon ID to fetch
     * @return Pokemon details or null if not found
     */
    suspend operator fun invoke(id: Int): Pokemon? {
        return pokemonRepository.getPokemonById(id)
    }
}
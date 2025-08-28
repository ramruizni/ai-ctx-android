package com.ramruizni.deepseekpokeappfive.pokemon.datasource

import com.ramruizni.deepseekpokeappfive.pokemon.domain.Pokemon

interface PokemonDataSource {
    suspend fun fetchPokemonListFromRemote(limit: Int = 20, offset: Int = 0): Result<List<Pokemon>>
    
    suspend fun fetchPokemonDetailFromRemote(pokemonName: String): Result<Pokemon>
}
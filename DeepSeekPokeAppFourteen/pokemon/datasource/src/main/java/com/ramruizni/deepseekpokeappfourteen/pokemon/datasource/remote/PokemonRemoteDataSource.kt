package com.ramruizni.deepseekpokeappfourteen.pokemon.datasource.remote

interface PokemonRemoteDataSource {
    suspend fun getPokemonList(offset: Int = 0, limit: Int = 20): PokemonListResponse
    suspend fun getPokemonDetails(id: Int): PokemonDetailsResponse
}
package com.ramruizni.deepseekpokeappfourteen.pokemon.datasource.remote

class PokemonRemoteDataSourceImpl(
    private val apiService: PokemonApiService
) : PokemonRemoteDataSource {

    override suspend fun getPokemonList(offset: Int, limit: Int): PokemonListResponse {
        return apiService.getPokemonList(offset, limit)
    }

    override suspend fun getPokemonDetails(id: Int): PokemonDetailsResponse {
        return apiService.getPokemonDetails(id)
    }
}
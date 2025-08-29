package com.example.deepseekpokeappeleven.pokemon.datasource.remote

import com.example.deepseekpokeappeleven.pokemon.datasource.dbdtos.PokemonDbDto

interface PokemonRemoteDataSource {
    suspend fun getPokemonList(limit: Int, offset: Int): List<PokemonDbDto>
    suspend fun getPokemonDetail(id: Int): PokemonDbDto
}
package com.example.deepseekpokeappten.pokemon.datasource

import com.example.deepseekpokeappten.database.daos.PokemonDao
import com.example.deepseekpokeappten.database.entities.PokemonEntity
import com.example.deepseekpokeappten.pokemon.datasource.remote.PokemonApiService
import com.example.deepseekpokeappten.pokemon.datasource.remote.PokemonDto
import com.example.deepseekpokeappten.pokemon.datasource.remote.PokemonListItemDto
import kotlinx.coroutines.flow.Flow

class PokemonDataSourceImpl(
    private val apiService: PokemonApiService,
    private val dao: PokemonDao
) : PokemonDataSource {

    override suspend fun fetchPokemonList(limit: Int, offset: Int): List<PokemonListItemDto> {
        return apiService.getPokemonList(limit, offset).results
    }

    override suspend fun fetchPokemon(id: Int): PokemonDto {
        return apiService.getPokemon(id)
    }

    override suspend fun fetchPokemon(name: String): PokemonDto {
        return apiService.getPokemon(name)
    }

    override fun getAllPokemon(): Flow<List<PokemonEntity>> {
        return dao.getAllPokemon()
    }

    override suspend fun getPokemonById(id: Int): PokemonEntity? {
        return dao.getPokemonById(id)
    }

    override suspend fun getPokemonByName(name: String): PokemonEntity? {
        return dao.getPokemonByName(name)
    }

    override suspend fun savePokemon(pokemon: PokemonEntity) {
        dao.insertPokemon(pokemon)
    }

    override suspend fun savePokemonList(pokemonList: List<PokemonEntity>) {
        dao.insertPokemonList(pokemonList)
    }

    override suspend fun clearAllPokemon() {
        dao.clearAllPokemon()
    }
}
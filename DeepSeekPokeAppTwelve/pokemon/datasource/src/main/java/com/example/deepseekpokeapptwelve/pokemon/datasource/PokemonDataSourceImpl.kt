package com.example.deepseekpokeapptwelve.pokemon.datasource

import com.example.deepseekpokeapptwelve.pokemon.datasource.converters.convertToDbDto
import com.example.deepseekpokeapptwelve.pokemon.datasource.converters.convertToDomain
import com.example.deepseekpokeapptwelve.pokemon.datasource.converters.toDomain
import com.example.deepseekpokeapptwelve.pokemon.datasource.daos.PokemonDao
import com.example.deepseekpokeapptwelve.pokemon.datasource.network.PokemonApiService
import com.example.deepseekpokeapptwelve.pokemon.domain.models.Pokemon
import com.example.deepseekpokeapptwelve.pokemon.domain.models.PokemonListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonDataSourceImpl(
    private val pokemonDao: PokemonDao,
    private val pokemonApiService: PokemonApiService
) : PokemonDataSource {

    override suspend fun fetchPokemonList(limit: Int, offset: Int): List<PokemonListItem> {
        return pokemonApiService.getPokemonList(limit, offset).results.map { it.toDomain() }
    }

    override suspend fun fetchPokemonDetails(name: String): Pokemon {
        return pokemonApiService.getPokemon(name).toDomain()
    }

    override fun getAllPokemon(): Flow<List<Pokemon>> {
        return pokemonDao.getAll().map { list ->
            list.map { it.convertToDomain() }
        }
    }

    override suspend fun savePokemon(pokemon: Pokemon) {
        pokemonDao.insert(pokemon.convertToDbDto())
    }

    override suspend fun savePokemonList(pokemonList: List<Pokemon>) {
        val dbDtos = pokemonList.map { it.convertToDbDto() }
        pokemonDao.insertAll(dbDtos)
    }

    override suspend fun getPokemonById(id: Int): Pokemon? {
        return pokemonDao.getById(id)?.convertToDomain()
    }

    override suspend fun getPokemonByName(name: String): Pokemon? {
        return pokemonDao.getByName(name)?.convertToDomain()
    }
}
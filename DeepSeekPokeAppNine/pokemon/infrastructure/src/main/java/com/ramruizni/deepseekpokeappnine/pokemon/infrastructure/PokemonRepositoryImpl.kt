package com.ramruizni.deepseekpokeappnine.pokemon.infrastructure

import com.ramruizni.deepseekpokeappnine.pokemon.datasource.PokemonDataSource
import com.ramruizni.deepseekpokeappnine.pokemon.datasource.mappers.toDomain
import com.ramruizni.deepseekpokeappnine.pokemon.domain.Pokemon
import com.ramruizni.deepseekpokeappnine.pokemon.domain.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonRepositoryImpl(
    private val dataSource: PokemonDataSource
) : PokemonRepository {
    
    override suspend fun getPokemonList(limit: Int, offset: Int): List<Pokemon> {
        return try {
            val pokemonDbList = dataSource.getPokemonFromApi(limit, offset)
            pokemonDbList.map { it.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    override fun getAllPokemonFromCache(): Flow<List<Pokemon>> {
        return dataSource.getAllPokemonFromDb().map { pokemonDbList ->
            pokemonDbList.map { it.toDomain() }
        }
    }
    
    override suspend fun getPokemonById(id: Int): Pokemon? {
        return dataSource.getPokemonByIdFromDb(id)?.toDomain()
    }
    
    override suspend fun refreshPokemonList(limit: Int, offset: Int): List<Pokemon> {
        return try {
            val pokemonDbList = dataSource.getPokemonFromApi(limit, offset)
            dataSource.savePokemonToDb(pokemonDbList)
            pokemonDbList.map { it.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
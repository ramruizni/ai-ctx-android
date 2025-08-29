package com.ramruizni.deepseekpokeappnine.pokemon.datasource

import com.ramruizni.deepseekpokeappnine.pokemon.datasource.daos.PokemonDao
import com.ramruizni.deepseekpokeappnine.pokemon.datasource.dbdtos.PokemonDbDto
import com.ramruizni.deepseekpokeappnine.pokemon.datasource.mappers.toDbDto
import com.ramruizni.deepseekpokeappnine.pokemon.datasource.remote.PokeApiService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow

class PokemonDataSourceImpl(
    private val pokemonDao: PokemonDao,
    private val pokeApiService: PokeApiService
) : PokemonDataSource {
    
    override suspend fun getPokemonFromApi(limit: Int, offset: Int): List<PokemonDbDto> {
        val pokemonList = pokeApiService.getPokemonList(limit, offset)
        
        // Extract ID from URL and fetch details for each Pokemon
        return coroutineScope {
            pokemonList.results.map { basicPokemon ->
                async {
                    val id = basicPokemon.url.split("/").dropLast(1).last().toInt()
                    val detail = pokeApiService.getPokemonDetail(id)
                    detail.toDbDto()
                }
            }.awaitAll()
        }
    }
    
    override fun getAllPokemonFromDb(): Flow<List<PokemonDbDto>> {
        return pokemonDao.getAllPokemon()
    }
    
    override suspend fun getPokemonByIdFromDb(id: Int): PokemonDbDto? {
        return pokemonDao.getPokemonById(id)
    }
    
    override suspend fun savePokemonToDb(pokemon: List<PokemonDbDto>) {
        pokemonDao.insertPokemon(pokemon)
    }
    
    override suspend fun clearPokemonDb() {
        pokemonDao.deleteAllPokemon()
    }
}
package com.ramruizni.deepseekpokeappsix.pokemon.datasource

import com.ramruizni.deepseekpokeappsix.database.daos.pokemon.PokemonDao
import com.ramruizni.deepseekpokeappsix.pokemon.datasource.local.toDomain
import com.ramruizni.deepseekpokeappsix.pokemon.datasource.local.toDbDto
import com.ramruizni.deepseekpokeappsix.pokemon.datasource.network.PokemonApiService
import com.ramruizni.deepseekpokeappsix.pokemon.datasource.network.extractId
import com.ramruizni.deepseekpokeappsix.pokemon.datasource.network.toDbDto
import com.ramruizni.deepseekpokeappsix.pokemon.datasource.network.toDomain
import com.ramruizni.deepseekpokeappsix.pokemon.domain.models.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class PokemonDataSourceImpl(
    private val pokemonDao: PokemonDao,
    private val pokemonApiService: PokemonApiService
) : PokemonDataSource {
    
    override suspend fun getAllPokemon(): Flow<List<Pokemon>> = flow {
        // First emit cached data
        val cachedPokemon = pokemonDao.getAll().map { it.toDomain() }
        emit(cachedPokemon)
        
        // Then try to refresh from network in background
        if (cachedPokemon.isEmpty()) {
            refreshPokemon()
            emit(pokemonDao.getAll().map { it.toDomain() })
        }
    }
    
    override suspend fun getPokemonById(id: Int): Pokemon? {
        // Check local first
        val cached = pokemonDao.getById(id)?.toDomain()
        if (cached != null) return cached
        
        // Fetch from network if not found locally
        return try {
            val networkPokemon = pokemonApiService.getPokemon(id)
            val dbDto = networkPokemon.toDbDto()
            pokemonDao.insert(dbDto)
            networkPokemon.toDomain()
        } catch (e: Exception) {
            null
        }
    }
    
    override suspend fun searchPokemon(name: String): Flow<List<Pokemon>> = flow {
        emit(pokemonDao.getByNameLike(name.lowercase()).map { it.toDomain() })
    }
    
    override suspend fun refreshPokemon(): Result<Unit> {
        return try {
            // Get list of first 151 Pokemon (original generation)
            val pokemonList = pokemonApiService.getPokemonList(limit = 151, offset = 0)
            
            val pokemonDetails = pokemonList.results.map { listItem ->
                val pokemonId = listItem.extractId()
                pokemonApiService.getPokemon(pokemonId)
            }
            
            val pokemonDbDtos = pokemonDetails.map { pokemonDetail -> pokemonDetail.toDbDto() }
            pokemonDao.insertAll(pokemonDbDtos)
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
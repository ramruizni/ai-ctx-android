package com.ramruizni.deepseekpokeappfive.pokemon.infrastructure

import com.ramruizni.deepseekpokeappfive.pokemon.datasource.PokemonDataSource
import com.ramruizni.deepseekpokeappfive.pokemon.datasource.daos.PokemonDao
import com.ramruizni.deepseekpokeappfive.pokemon.datasource.mappers.PokemonMapper
import com.ramruizni.deepseekpokeappfive.pokemon.domain.Pokemon
import com.ramruizni.deepseekpokeappfive.pokemon.domain.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val remoteDataSource: PokemonDataSource,
    private val localDataSource: PokemonDao,
    private val mapper: PokemonMapper
) : PokemonRepository {
    
    override suspend fun fetchAndSavePokemonList(limit: Int, offset: Int): Result<Unit> {
        return try {
            val result = remoteDataSource.fetchPokemonListFromRemote(limit, offset)
            if (result.isSuccess) {
                val pokemonList = result.getOrThrow()
                val dbDtos = pokemonList.map { mapper.toDbDto(it) }
                localDataSource.insertAll(dbDtos)
                Result.success(Unit)
            } else {
                Result.failure(result.exceptionOrNull() ?: Exception("Failed to fetch Pokemon list"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override fun getAllPokemon(): Flow<List<Pokemon>> {
        return localDataSource.getAll().map { dbDtos ->
            dbDtos.map { mapper.toDomain(it) }
        }
    }
    
    override fun getPokemonById(id: Int): Flow<Pokemon?> {
        return localDataSource.getById(id).map { dbDto ->
            dbDto?.let { mapper.toDomain(it) }
        }
    }
    
    override suspend fun refreshPokemonData(): Result<Unit> {
        return try {
            localDataSource.deleteAll()
            fetchAndSavePokemonList(limit = 151) // Fetch first 151 Pokemon
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun clearAllPokemon(): Result<Unit> {
        return try {
            localDataSource.deleteAll()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
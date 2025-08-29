package com.ramruizni.deepseekpokeappeight.pokemon.datasource

import com.ramruizni.deepseekpokeappeight.pokemon.local.PokemonDao
import com.ramruizni.deepseekpokeappeight.pokemon.local.PokemonWithDetails
import com.ramruizni.deepseekpokeappeight.pokemon.datasource.remote.PokemonApiService
import com.ramruizni.deepseekpokeappeight.pokemon.datasource.remote.PokemonDetailResponse
import com.ramruizni.deepseekpokeappeight.pokemon.datasource.remote.PokemonListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class PokemonDataSourceImpl(
    private val apiService: PokemonApiService,
    private val pokemonDao: PokemonDao
) : PokemonDataSource {
    
    override suspend fun fetchPokemonList(limit: Int, offset: Int): Result<PokemonListResponse> {
        return try {
            val response = apiService.getPokemonList(limit, offset)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun fetchPokemonDetails(id: Int): Result<PokemonDetailResponse> {
        return try {
            val response = apiService.getPokemonDetails(id)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun fetchPokemonDetailsByName(name: String): Result<PokemonDetailResponse> {
        return try {
            val response = apiService.getPokemonDetailsByName(name)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override fun getAllPokemonFromCache(): Flow<List<PokemonWithDetails>> {
        return pokemonDao.getAllPokemonWithDetails()
    }
    
    override suspend fun getPokemonFromCache(id: Int): PokemonWithDetails? {
        return pokemonDao.getPokemonWithDetails(id)
    }
    
    override suspend fun cachePokemonDetails(pokemonDetail: PokemonDetailResponse) {
        val pokemon = PokemonConverter.fromApiResponseToDbDto(pokemonDetail)
        val types = PokemonConverter.fromApiResponseToTypeDbDtos(pokemonDetail.id, pokemonDetail)
        val abilities = PokemonConverter.fromApiResponseToAbilityDbDtos(pokemonDetail.id, pokemonDetail)
        val stats = PokemonConverter.fromApiResponseToStatDbDtos(pokemonDetail.id, pokemonDetail)
        
        pokemonDao.insertPokemonWithDetails(pokemon, types, abilities, stats)
    }
    
    override suspend fun clearPokemonCache() {
        pokemonDao.deleteAllPokemonStats()
        pokemonDao.deleteAllPokemonAbilities()
        pokemonDao.deleteAllPokemonTypes()
        pokemonDao.deleteAllPokemon()
    }
    
    override suspend fun getPokemonList(
        limit: Int,
        offset: Int,
        forceRefresh: Boolean
    ): Flow<List<PokemonWithDetails>> = flow {
        // Emit cached data first (if available and not forcing refresh)
        if (!forceRefresh) {
            val cachedPokemon = pokemonDao.getAllPokemonWithDetails().first()
            if (cachedPokemon.isNotEmpty()) {
                emit(cachedPokemon)
            }
        }
        
        try {
            // Fetch from API
            val apiResult = fetchPokemonList(limit, offset)
            apiResult.getOrNull()?.let { response ->
                // For each Pokemon in the list, fetch and cache details
                response.results.forEach { pokemonApiDto ->
                    // Extract ID from URL (e.g., "https://pokeapi.co/api/v2/pokemon/1/" -> 1)
                    val pokemonId = pokemonApiDto.url.trimEnd('/').substringAfterLast('/').toIntOrNull()
                    
                    pokemonId?.let { id ->
                        // Check if we already have this Pokemon cached (unless forcing refresh)
                        val existsInCache = if (!forceRefresh) {
                            pokemonDao.getPokemonWithDetails(id) != null
                        } else false
                        
                        if (!existsInCache) {
                            // Fetch and cache Pokemon details
                            fetchPokemonDetails(id).getOrNull()?.let { details ->
                                cachePokemonDetails(details)
                            }
                        }
                    }
                }
                
                // Emit updated cached data
                val updatedPokemon = pokemonDao.getAllPokemonWithDetails().first()
                emit(updatedPokemon)
            }
        } catch (e: Exception) {
            // If network fails, still try to emit any cached data
            val cachedPokemon = pokemonDao.getAllPokemonWithDetails().first()
            if (cachedPokemon.isNotEmpty()) {
                emit(cachedPokemon)
            } else {
                throw e
            }
        }
    }
    
    override suspend fun getPokemonDetails(
        id: Int,
        forceRefresh: Boolean
    ): Result<PokemonWithDetails> {
        return try {
            // Check cache first (unless forcing refresh)
            if (!forceRefresh) {
                val cachedPokemon = pokemonDao.getPokemonWithDetails(id)
                if (cachedPokemon != null) {
                    return Result.success(cachedPokemon)
                }
            }
            
            // Fetch from API and cache
            val apiResult = fetchPokemonDetails(id)
            apiResult.fold(
                onSuccess = { response ->
                    cachePokemonDetails(response)
                    val cachedPokemon = pokemonDao.getPokemonWithDetails(id)
                    if (cachedPokemon != null) {
                        Result.success(cachedPokemon)
                    } else {
                        Result.failure(Exception("Failed to cache Pokemon details"))
                    }
                },
                onFailure = { error ->
                    // If network fails, try to return cached data
                    val cachedPokemon = pokemonDao.getPokemonWithDetails(id)
                    if (cachedPokemon != null) {
                        Result.success(cachedPokemon)
                    } else {
                        Result.failure(error)
                    }
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
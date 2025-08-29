package com.ramruizni.deepseekpokeappfourteen.pokemon.infrastructure.repository

import com.ramruizni.deepseekpokeappfourteen.pokemon.datasource.PokemonDataSource
import com.ramruizni.deepseekpokeappfourteen.pokemon.datasource.remote.PokemonRemoteDataSource
import com.ramruizni.deepseekpokeappfourteen.pokemon.domain.Pokemon
import com.ramruizni.deepseekpokeappfourteen.pokemon.domain.PokemonRepository
import com.ramruizni.deepseekpokeappfourteen.pokemon.infrastructure.mappers.toDomain
import com.ramruizni.deepseekpokeappfourteen.pokemon.infrastructure.mappers.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class PokemonRepositoryImpl(
    private val localDataSource: PokemonDataSource,
    private val remoteDataSource: PokemonRemoteDataSource
) : PokemonRepository {

    override suspend fun getAllPokemon(offset: Int, limit: Int): List<Pokemon> {
        return localDataSource.getAllPokemon().map { entities ->
            entities.map { it.toDomain() }
        }.let { flow ->
            var result: List<Pokemon> = emptyList()
            flow.collect { result = it }
            result
        }
    }

    override fun getAllPokemonFlow(): Flow<List<Pokemon>> {
        return localDataSource.getAllPokemon().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getPokemonById(id: Int): Pokemon? {
        return localDataSource.getPokemonById(id)?.toDomain()
    }

    override suspend fun refreshPokemon(offset: Int, limit: Int) {
        try {
            // Get Pokemon list from API
            val pokemonListResponse = remoteDataSource.getPokemonList(offset, limit)
            
            // Fetch details for each Pokemon in parallel
            val pokemonDetails = coroutineScope {
                pokemonListResponse.results.map { pokemonItem ->
                    async {
                        try {
                            remoteDataSource.getPokemonDetails(pokemonItem.getId())
                        } catch (e: Exception) {
                            null
                        }
                    }
                }.awaitAll().filterNotNull()
            }
            
            // Convert to entities and save to local database
            val pokemonEntities = pokemonDetails.map { detailsResponse ->
                detailsResponse.toDomain().toEntity()
            }
            
            if (pokemonEntities.isNotEmpty()) {
                localDataSource.insertAll(pokemonEntities)
            }
            
        } catch (e: Exception) {
            // If network fails, we still have local data to show
            // Log error here if needed
        }
    }
}
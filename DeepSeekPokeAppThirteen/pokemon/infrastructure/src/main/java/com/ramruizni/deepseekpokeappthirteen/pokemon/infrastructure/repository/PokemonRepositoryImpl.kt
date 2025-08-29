package com.ramruizni.deepseekpokeappthirteen.pokemon.infrastructure.repository

import com.ramruizni.deepseekpokeappthirteen.pokemon.datasource.local.PokemonDataSource
import com.ramruizni.deepseekpokeappthirteen.pokemon.datasource.remote.PokemonApiService
import com.ramruizni.deepseekpokeappthirteen.pokemon.domain.model.Pokemon
import com.ramruizni.deepseekpokeappthirteen.pokemon.domain.repository.PokemonRepository
import com.ramruizni.deepseekpokeappthirteen.pokemon.infrastructure.mappers.toDbDto
import com.ramruizni.deepseekpokeappthirteen.pokemon.infrastructure.mappers.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class PokemonRepositoryImpl(
    private val pokemonDataSource: PokemonDataSource,
    private val pokemonApiService: PokemonApiService
) : PokemonRepository {
    
    override suspend fun fetchAndStorePokemonList(limit: Int): List<Pokemon> {
        return try {
            val pokemonListResponse = pokemonApiService.getPokemonList(limit)
            
            // Fetch detailed information for each Pokemon in parallel
            val pokemonDetails = coroutineScope {
                pokemonListResponse.results.mapIndexed { index, pokemonListItem ->
                    async {
                        try {
                            pokemonApiService.getPokemonDetail(index + 1)
                        } catch (e: Exception) {
                            null
                        }
                    }
                }.awaitAll().filterNotNull()
            }
            
            // Convert to database DTOs and store
            val pokemonDbDtos = pokemonDetails.map { it.toDbDto() }
            pokemonDataSource.insertAllPokemon(pokemonDbDtos)
            
            // Return domain models
            pokemonDbDtos.map { it.toDomain() }
        } catch (e: Exception) {
            // If network fails, return empty list
            // In a production app, you might want to handle this differently
            emptyList()
        }
    }
    
    override fun getAllPokemon(): Flow<List<Pokemon>> {
        return pokemonDataSource.getAllPokemon().map { pokemonList ->
            pokemonList.map { it.toDomain() }
        }
    }
    
    override suspend fun getPokemonById(id: Int): Pokemon? {
        return pokemonDataSource.getPokemonById(id)?.toDomain()
    }
    
    override suspend fun refreshPokemonList(): List<Pokemon> {
        pokemonDataSource.deleteAllPokemon()
        return fetchAndStorePokemonList()
    }
}
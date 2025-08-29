package com.ramruizni.deepseekpokeappseven.pokemon.datasource

import com.ramruizni.deepseekpokeappseven.database.entities.PokemonDao
import com.ramruizni.deepseekpokeappseven.database.entities.PokemonDbDto
import com.ramruizni.deepseekpokeappseven.pokemon.datasource.remote.PokemonApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class PokemonDataSourceImpl(
    private val pokemonDao: PokemonDao,
    private val pokemonApiService: PokemonApiService
) : PokemonDataSource {

    override suspend fun getPokemonList(limit: Int, offset: Int): List<PokemonDbDto> {
        return pokemonDao.getAllPokemon()
    }

    override suspend fun getPokemonById(id: Int): PokemonDbDto? {
        return pokemonDao.getPokemonById(id)
    }

    override suspend fun searchPokemon(query: String): List<PokemonDbDto> {
        return pokemonDao.searchPokemon(query)
    }

    override fun observePokemonList(): Flow<List<PokemonDbDto>> {
        return pokemonDao.observeAllPokemon()
    }

    override suspend fun refreshPokemonList() {
        try {
            val pokemonListResponse = pokemonApiService.getPokemonList(limit = 151, offset = 0)
            
            val pokemonDetails = coroutineScope {
                pokemonListResponse.results.mapIndexed { index, pokemonListItem ->
                    async {
                        try {
                            val id = index + 1
                            val detail = pokemonApiService.getPokemonDetail(id)
                            mapToPokemonDbDto(detail)
                        } catch (e: Exception) {
                            null
                        }
                    }
                }.awaitAll().filterNotNull()
            }
            
            pokemonDao.insertPokemon(pokemonDetails)
        } catch (e: Exception) {
            // Handle error - could log or throw depending on requirements
        }
    }

    private fun mapToPokemonDbDto(detail: com.ramruizni.deepseekpokeappseven.pokemon.datasource.remote.PokemonDetailResponse): PokemonDbDto {
        return PokemonDbDto(
            id = detail.id,
            name = detail.name,
            imageUrl = detail.sprites.frontDefault ?: "",
            types = detail.types.joinToString("|") { "${it.type.name},${getTypeColor(it.type.name)}" },
            abilities = detail.abilities.joinToString("|") { "${it.ability.name},${it.isHidden}" },
            stats = detail.stats.joinToString("|") { "${it.stat.name},${it.baseStat},${it.effort}" },
            height = detail.height,
            weight = detail.weight
        )
    }

    private fun getTypeColor(typeName: String): String {
        return when (typeName.lowercase()) {
            "normal" -> "#A8A878"
            "fire" -> "#F08030"
            "water" -> "#6890F0"
            "electric" -> "#F8D030"
            "grass" -> "#78C850"
            "ice" -> "#98D8D8"
            "fighting" -> "#C03028"
            "poison" -> "#A040A0"
            "ground" -> "#E0C068"
            "flying" -> "#A890F0"
            "psychic" -> "#F85888"
            "bug" -> "#A8B820"
            "rock" -> "#B8A038"
            "ghost" -> "#705898"
            "dragon" -> "#7038F8"
            "dark" -> "#705848"
            "steel" -> "#B8B8D0"
            "fairy" -> "#EE99AC"
            else -> "#68A090"
        }
    }
}
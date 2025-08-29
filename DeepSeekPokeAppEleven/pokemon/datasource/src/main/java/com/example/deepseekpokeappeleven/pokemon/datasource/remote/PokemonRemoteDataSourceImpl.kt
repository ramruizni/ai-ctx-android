package com.example.deepseekpokeappeleven.pokemon.datasource.remote

import com.example.deepseekpokeappeleven.pokemon.datasource.api.PokeApiService
import com.example.deepseekpokeappeleven.pokemon.datasource.api.PokemonDetailResponse
import com.example.deepseekpokeappeleven.pokemon.datasource.dbdtos.PokemonAbilityDbDto
import com.example.deepseekpokeappeleven.pokemon.datasource.dbdtos.PokemonDbDto
import com.example.deepseekpokeappeleven.pokemon.datasource.dbdtos.PokemonStatDbDto
import com.example.deepseekpokeappeleven.pokemon.datasource.dbdtos.PokemonTypeDbDto
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class PokemonRemoteDataSourceImpl(
    private val pokeApiService: PokeApiService
) : PokemonRemoteDataSource {

    override suspend fun getPokemonList(limit: Int, offset: Int): List<PokemonDbDto> {
        val pokemonListResponse = pokeApiService.getPokemonList(limit, offset)
        
        return coroutineScope {
            pokemonListResponse.results.map { pokemonBasic ->
                async {
                    val detail = pokeApiService.getPokemonDetail(pokemonBasic.id)
                    detail.toDbDto()
                }
            }.awaitAll()
        }
    }

    override suspend fun getPokemonDetail(id: Int): PokemonDbDto {
        val detail = pokeApiService.getPokemonDetail(id)
        return detail.toDbDto()
    }

    private fun PokemonDetailResponse.toDbDto(): PokemonDbDto {
        return PokemonDbDto(
            id = id,
            name = name,
            number = id,
            imageUrl = sprites.frontDefault ?: "",
            types = types.map { typeSlot ->
                PokemonTypeDbDto(
                    name = typeSlot.type.name,
                    color = null // Can be enhanced later with type color mapping
                )
            },
            height = height,
            weight = weight,
            abilities = abilities.map { abilitySlot ->
                PokemonAbilityDbDto(
                    name = abilitySlot.ability.name,
                    isHidden = abilitySlot.isHidden
                )
            },
            stats = stats.map { statSlot ->
                PokemonStatDbDto(
                    name = statSlot.stat.name,
                    baseStat = statSlot.baseStat,
                    effort = statSlot.effort
                )
            }
        )
    }
}
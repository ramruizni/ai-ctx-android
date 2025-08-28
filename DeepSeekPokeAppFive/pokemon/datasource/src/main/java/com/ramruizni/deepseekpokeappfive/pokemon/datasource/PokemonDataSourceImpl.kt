package com.ramruizni.deepseekpokeappfive.pokemon.datasource

import com.ramruizni.deepseekpokeappfive.pokemon.datasource.remote.PokeApiService
import com.ramruizni.deepseekpokeappfive.pokemon.domain.Pokemon
import com.ramruizni.deepseekpokeappfive.pokemon.domain.PokemonType
import javax.inject.Inject

class PokemonDataSourceImpl @Inject constructor(
    private val pokeApiService: PokeApiService
) : PokemonDataSource {
    
    override suspend fun fetchPokemonListFromRemote(limit: Int, offset: Int): Result<List<Pokemon>> {
        return try {
            val response = pokeApiService.getPokemonList(limit, offset)
            val pokemonList = mutableListOf<Pokemon>()
            
            for (item in response.results) {
                val detailResult = fetchPokemonDetailFromRemote(item.name)
                if (detailResult.isSuccess) {
                    pokemonList.add(detailResult.getOrThrow())
                }
            }
            
            Result.success(pokemonList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun fetchPokemonDetailFromRemote(pokemonName: String): Result<Pokemon> {
        return try {
            val response = pokeApiService.getPokemonDetail(pokemonName)
            
            val pokemon = Pokemon(
                id = response.id,
                name = response.name.replaceFirstChar { it.uppercase() },
                number = response.id,
                imageUrl = response.sprites.front_default ?: "",
                types = response.types.map { 
                    PokemonType.valueOf(it.type.name.uppercase())
                },
                abilities = response.abilities.map {
                    Pokemon.Ability(
                        name = it.ability.name.replace("-", " ").replaceFirstChar { char -> char.uppercase() },
                        isHidden = it.is_hidden
                    )
                },
                stats = response.stats.map {
                    Pokemon.Stat(
                        name = it.stat.name.replace("-", " ").replaceFirstChar { char -> char.uppercase() },
                        baseValue = it.base_stat
                    )
                }
            )
            
            Result.success(pokemon)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
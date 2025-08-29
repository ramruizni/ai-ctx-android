package com.example.deepseekpokeappeleven.pokemon.infrastructure.repositories

import com.example.deepseekpokeappeleven.pokemon.datasource.PokemonDataSource
import com.example.deepseekpokeappeleven.pokemon.domain.models.Pokemon
import com.example.deepseekpokeappeleven.pokemon.domain.models.PokemonAbility
import com.example.deepseekpokeappeleven.pokemon.domain.models.PokemonStat
import com.example.deepseekpokeappeleven.pokemon.domain.models.PokemonType
import com.example.deepseekpokeappeleven.pokemon.domain.repositories.PokemonRepository
import com.example.deepseekpokeappeleven.pokemon.datasource.dbdtos.PokemonDbDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonRepositoryImpl(
    private val dataSource: PokemonDataSource
) : PokemonRepository {

    override fun getPokemonList(limit: Int, offset: Int): Flow<List<Pokemon>> {
        return dataSource.getPokemonList(limit, offset).map { dbDtos ->
            dbDtos.map { it.toDomain() }
        }
    }

    override suspend fun refreshPokemonList(limit: Int, offset: Int) {
        dataSource.refreshPokemonList(limit, offset)
    }

    override suspend fun getPokemonById(id: Int): Pokemon? {
        return dataSource.getPokemonById(id)?.toDomain()
    }

    private fun PokemonDbDto.toDomain(): Pokemon {
        return Pokemon(
            id = id,
            name = name,
            number = number,
            imageUrl = imageUrl,
            types = types.map { typeDto ->
                PokemonType(
                    name = typeDto.name,
                    color = typeDto.color
                )
            },
            height = height,
            weight = weight,
            abilities = abilities.map { abilityDto ->
                PokemonAbility(
                    name = abilityDto.name,
                    isHidden = abilityDto.isHidden
                )
            },
            stats = stats.map { statDto ->
                PokemonStat(
                    name = statDto.name,
                    baseStat = statDto.baseStat,
                    effort = statDto.effort
                )
            }
        )
    }
}
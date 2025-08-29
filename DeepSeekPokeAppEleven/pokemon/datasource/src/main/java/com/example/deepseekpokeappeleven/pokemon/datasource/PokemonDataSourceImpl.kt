package com.example.deepseekpokeappeleven.pokemon.datasource

import com.example.deepseekpokeappeleven.pokemon.datasource.dbdtos.PokemonDbDto
import com.example.deepseekpokeappeleven.pokemon.datasource.local.PokemonLocalDataSource
import com.example.deepseekpokeappeleven.pokemon.datasource.remote.PokemonRemoteDataSource
import com.example.deepseekpokeappeleven.pokemon.datasource.entities.PokemonEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonDataSourceImpl(
    private val localDataSource: PokemonLocalDataSource,
    private val remoteDataSource: PokemonRemoteDataSource
) : PokemonDataSource {

    override fun getPokemonList(limit: Int, offset: Int): Flow<List<PokemonDbDto>> {
        return localDataSource.getPokemonList(limit, offset).map { entities ->
            entities.map { it.toDbDto() }
        }
    }

    override suspend fun refreshPokemonList(limit: Int, offset: Int) {
        try {
            val remotePokemon = remoteDataSource.getPokemonList(limit, offset)
            val entities = remotePokemon.map { it.toEntity() }
            localDataSource.insertPokemon(entities)
        } catch (e: Exception) {
            // In a production app, you might want to handle this more gracefully
            // For now, we'll let the cached data be used
        }
    }

    override suspend fun getPokemonById(id: Int): PokemonDbDto? {
        return localDataSource.getPokemonById(id)?.toDbDto()
    }

    private fun PokemonEntity.toDbDto(): PokemonDbDto {
        return PokemonDbDto(
            id = id,
            name = name,
            number = number,
            imageUrl = imageUrl,
            types = types,
            height = height,
            weight = weight,
            abilities = abilities,
            stats = stats
        )
    }

    private fun PokemonDbDto.toEntity(): PokemonEntity {
        return PokemonEntity(
            id = id,
            name = name,
            number = number,
            imageUrl = imageUrl,
            types = types,
            height = height,
            weight = weight,
            abilities = abilities,
            stats = stats
        )
    }
}
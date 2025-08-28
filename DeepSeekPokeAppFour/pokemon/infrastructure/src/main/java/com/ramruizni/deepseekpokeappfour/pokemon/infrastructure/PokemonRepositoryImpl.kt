package com.ramruizni.deepseekpokeappfour.pokemon.infrastructure

import com.ramruizni.deepseekpokeappfour.pokemon.datasource.PokemonDataSource
import com.ramruizni.deepseekpokeappfour.pokemon.datasource.PokemonDbDto
import com.ramruizni.deepseekpokeappfour.pokemon.datasource.PokemonJsonConverter.toJsonString
import com.ramruizni.deepseekpokeappfour.pokemon.datasource.PokemonJsonConverter.fromJsonStringToList
import com.ramruizni.deepseekpokeappfour.pokemon.domain.models.Pokemon
import com.ramruizni.deepseekpokeappfour.pokemon.domain.models.PokemonStats
import com.ramruizni.deepseekpokeappfour.pokemon.domain.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonRepositoryImpl(
    private val localDataSource: PokemonDataSource
    // TODO: Add network data source when implementing PokeAPI integration
    // private val remoteDataSource: PokemonRemoteDataSource
) : PokemonRepository {
    
    override fun getAllPokemon(): Flow<List<Pokemon>> {
        return localDataSource.getAllPokemon().map { pokemonDtos ->
            pokemonDtos.map { it.toDomain() }
        }
    }
    
    override suspend fun refreshPokemon(): Result<Unit> {
        return try {
            // TODO: Implement network refresh logic when PokeAPI integration is added
            // For now, just return success
            Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
    
    override fun getPokemonById(id: Int): Flow<Pokemon?> {
        return localDataSource.getPokemonById(id).map { pokemonDto ->
            pokemonDto?.toDomain()
        }
    }
    
    // Additional repository methods for data management
    suspend fun insertPokemon(pokemon: List<Pokemon>) {
        localDataSource.insertPokemon(pokemon.map { it.toDbDto() })
    }
    
    suspend fun insertSinglePokemon(pokemon: Pokemon) {
        localDataSource.insertSinglePokemon(pokemon.toDbDto())
    }
    
    suspend fun updatePokemon(pokemon: Pokemon) {
        localDataSource.updatePokemon(pokemon.toDbDto())
    }
    
    suspend fun deletePokemon(pokemon: Pokemon) {
        localDataSource.deletePokemon(pokemon.toDbDto())
    }
    
    suspend fun deleteAllPokemon() = localDataSource.deleteAllPokemon()
    
    suspend fun getPokemonCount(): Int = localDataSource.getPokemonCount()
}

// Extension functions for domain-data conversion
private fun PokemonDbDto.toDomain(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        number = number,
        imageUrl = imageUrl,
        types = fromJsonStringToList(types),
        abilities = fromJsonStringToList(abilities),
        stats = PokemonStats(
            hp = statsHp,
            attack = statsAttack,
            defense = statsDefense,
            specialAttack = statsSpecialAttack,
            specialDefense = statsSpecialDefense,
            speed = statsSpeed
        )
    )
}

private fun Pokemon.toDbDto(): PokemonDbDto {
    return PokemonDbDto(
        id = id,
        name = name,
        number = number,
        imageUrl = imageUrl,
        types = toJsonString(types),
        abilities = toJsonString(abilities),
        statsHp = stats.hp,
        statsAttack = stats.attack,
        statsDefense = stats.defense,
        statsSpecialAttack = stats.specialAttack,
        statsSpecialDefense = stats.specialDefense,
        statsSpeed = stats.speed
    )
}
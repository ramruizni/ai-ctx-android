package com.ramruizni.deepseekpokeappthirteen.di.modules

import com.ramruizni.deepseekpokeappthirteen.database.daos.PokemonDatabaseDao
import com.ramruizni.deepseekpokeappthirteen.database.entities.PokemonEntity
import com.ramruizni.deepseekpokeappthirteen.pokemon.datasource.daos.PokemonDao
import com.ramruizni.deepseekpokeappthirteen.pokemon.datasource.dbdtos.PokemonDbDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonDaoAdapter(
    private val databaseDao: PokemonDatabaseDao
) : PokemonDao {
    
    override fun getAllPokemon(): Flow<List<PokemonDbDto>> {
        return databaseDao.getAllPokemon().map { entities ->
            entities.map { it.toDbDto() }
        }
    }
    
    override suspend fun getPokemonById(id: Int): PokemonDbDto? {
        return databaseDao.getPokemonById(id)?.toDbDto()
    }
    
    override suspend fun insertPokemon(pokemon: PokemonDbDto) {
        databaseDao.insertPokemon(pokemon.toEntity())
    }
    
    override suspend fun insertAllPokemon(pokemon: List<PokemonDbDto>) {
        databaseDao.insertAllPokemon(pokemon.map { it.toEntity() })
    }
    
    override suspend fun deleteAllPokemon() {
        databaseDao.deleteAllPokemon()
    }
    
    private fun PokemonEntity.toDbDto(): PokemonDbDto {
        return PokemonDbDto(
            id = this.id,
            name = this.name,
            imageUrl = this.imageUrl,
            height = this.height,
            weight = this.weight,
            baseExperience = this.baseExperience,
            types = this.types,
            abilities = this.abilities,
            stats = this.stats
        )
    }
    
    private fun PokemonDbDto.toEntity(): PokemonEntity {
        return PokemonEntity(
            id = this.id,
            name = this.name,
            imageUrl = this.imageUrl,
            height = this.height,
            weight = this.weight,
            baseExperience = this.baseExperience,
            types = this.types,
            abilities = this.abilities,
            stats = this.stats
        )
    }
}
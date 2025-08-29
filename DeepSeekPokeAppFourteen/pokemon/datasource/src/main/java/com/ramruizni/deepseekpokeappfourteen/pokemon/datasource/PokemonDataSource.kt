package com.ramruizni.deepseekpokeappfourteen.pokemon.datasource

import com.ramruizni.deepseekpokeappfourteen.database.entities.PokemonEntity
import kotlinx.coroutines.flow.Flow

interface PokemonDataSource {
    fun getAllPokemon(): Flow<List<PokemonEntity>>
    suspend fun getPokemonById(id: Int): PokemonEntity?
    suspend fun insertAll(pokemon: List<PokemonEntity>)
    suspend fun insert(pokemon: PokemonEntity)
    suspend fun deleteAll()
}
package com.ramruizni.deepseekpokeappnine.pokemon.datasource.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ramruizni.deepseekpokeappnine.pokemon.datasource.dbdtos.PokemonDbDto
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon ORDER BY id ASC")
    fun getAllPokemon(): Flow<List<PokemonDbDto>>

    @Query("SELECT * FROM pokemon WHERE id = :id")
    suspend fun getPokemonById(id: Int): PokemonDbDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: List<PokemonDbDto>)

    @Query("DELETE FROM pokemon")
    suspend fun deleteAllPokemon()
}
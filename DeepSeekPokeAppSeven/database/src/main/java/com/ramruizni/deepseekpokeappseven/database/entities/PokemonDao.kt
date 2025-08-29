package com.ramruizni.deepseekpokeappseven.database.entities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon ORDER BY id ASC")
    fun observeAllPokemon(): Flow<List<PokemonDbDto>>
    
    @Query("SELECT * FROM pokemon ORDER BY id ASC")
    suspend fun getAllPokemon(): List<PokemonDbDto>
    
    @Query("SELECT * FROM pokemon WHERE id = :id")
    suspend fun getPokemonById(id: Int): PokemonDbDto?
    
    @Query("SELECT * FROM pokemon WHERE name LIKE '%' || :query || '%' ORDER BY id ASC")
    suspend fun searchPokemon(query: String): List<PokemonDbDto>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: List<PokemonDbDto>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: PokemonDbDto)
    
    @Query("DELETE FROM pokemon")
    suspend fun clearAllPokemon()
}
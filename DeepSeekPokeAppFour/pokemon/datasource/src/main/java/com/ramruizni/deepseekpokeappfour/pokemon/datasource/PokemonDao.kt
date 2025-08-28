package com.ramruizni.deepseekpokeappfour.pokemon.datasource

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    
    @Query("SELECT * FROM pokemon ORDER BY number ASC")
    fun getAllPokemon(): Flow<List<PokemonDbDto>>
    
    @Query("SELECT * FROM pokemon WHERE id = :id")
    fun getPokemonById(id: Int): Flow<PokemonDbDto?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: List<PokemonDbDto>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSinglePokemon(pokemon: PokemonDbDto)
    
    @Update
    suspend fun updatePokemon(pokemon: PokemonDbDto)
    
    @Delete
    suspend fun deletePokemon(pokemon: PokemonDbDto)
    
    @Query("DELETE FROM pokemon")
    suspend fun deleteAllPokemon()
    
    @Query("SELECT COUNT(*) FROM pokemon")
    suspend fun getPokemonCount(): Int
}
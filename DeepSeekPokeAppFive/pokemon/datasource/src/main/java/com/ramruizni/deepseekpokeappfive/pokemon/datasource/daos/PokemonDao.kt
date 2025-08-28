package com.ramruizni.deepseekpokeappfive.pokemon.datasource.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ramruizni.deepseekpokeappfive.pokemon.datasource.dbdtos.PokemonDbDto
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: PokemonDbDto)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemon: List<PokemonDbDto>)
    
    @Query("SELECT * FROM pokemon ORDER BY number ASC")
    fun getAll(): Flow<List<PokemonDbDto>>
    
    @Query("SELECT * FROM pokemon WHERE id = :id")
    fun getById(id: Int): Flow<PokemonDbDto?>
    
    @Query("DELETE FROM pokemon")
    suspend fun deleteAll()
    
    @Query("SELECT COUNT(*) FROM pokemon")
    suspend fun getCount(): Int
}
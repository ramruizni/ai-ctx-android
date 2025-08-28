package com.ramruizni.deepseekpokeappsix.database.daos.pokemon

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ramruizni.deepseekpokeappsix.database.entities.pokemon.PokemonDbDto

@Dao
abstract class PokemonDao {

    @Query("SELECT * FROM pokemon ORDER BY id")
    abstract suspend fun getAll(): List<PokemonDbDto>
    
    @Query("SELECT * FROM pokemon WHERE id = :id")
    abstract suspend fun getById(id: Int): PokemonDbDto?
    
    @Query("SELECT * FROM pokemon WHERE name LIKE :name || '%' ORDER BY id")
    abstract suspend fun getByNameLike(name: String): List<PokemonDbDto>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(pokemon: List<PokemonDbDto>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(pokemon: PokemonDbDto)
    
    @Query("DELETE FROM pokemon")
    abstract suspend fun deleteAll()
}
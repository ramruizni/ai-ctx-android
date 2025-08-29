package com.example.deepseekpokeapptwelve.pokemon.datasource.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.deepseekpokeapptwelve.pokemon.datasource.dbdtos.PokemonDbDto
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PokemonDao {

    @Query("SELECT * FROM pokemon ORDER BY id")
    abstract fun getAll(): Flow<List<PokemonDbDto>>

    @Query("SELECT * FROM pokemon WHERE id = :id")
    abstract suspend fun getById(id: Int): PokemonDbDto?

    @Query("SELECT * FROM pokemon WHERE name = :name")
    abstract suspend fun getByName(name: String): PokemonDbDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(pokemon: PokemonDbDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(pokemon: List<PokemonDbDto>)

    @Query("DELETE FROM pokemon")
    abstract suspend fun deleteAll()
}
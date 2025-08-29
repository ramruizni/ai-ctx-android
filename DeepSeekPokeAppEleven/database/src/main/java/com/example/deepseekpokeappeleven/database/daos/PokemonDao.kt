package com.example.deepseekpokeappeleven.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.deepseekpokeappeleven.pokemon.datasource.entities.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon ORDER BY number ASC LIMIT :limit OFFSET :offset")
    fun getPokemonList(limit: Int, offset: Int): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon WHERE id = :id LIMIT 1")
    suspend fun getPokemonById(id: Int): PokemonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: List<PokemonEntity>)

    @Query("DELETE FROM pokemon")
    suspend fun clearAll()
}
package com.ramruizni.deepseekpokeappthirteen.database.daos

import androidx.room.*
import com.ramruizni.deepseekpokeappthirteen.database.entities.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDatabaseDao {
    @Query("SELECT * FROM pokemon ORDER BY id ASC")
    fun getAllPokemon(): Flow<List<PokemonEntity>>
    
    @Query("SELECT * FROM pokemon WHERE id = :id")
    suspend fun getPokemonById(id: Int): PokemonEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: PokemonEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPokemon(pokemon: List<PokemonEntity>)
    
    @Query("DELETE FROM pokemon")
    suspend fun deleteAllPokemon()
}
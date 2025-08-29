package com.ramruizni.deepseekpokeappeight.pokemon.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    
    @Query("SELECT * FROM pokemon ORDER BY id ASC")
    fun getAllPokemon(): Flow<List<PokemonDbDto>>
    
    @Query("SELECT * FROM pokemon WHERE id = :id")
    suspend fun getPokemonById(id: Int): PokemonDbDto?
    
    @Query("SELECT * FROM pokemon WHERE name = :name")
    suspend fun getPokemonByName(name: String): PokemonDbDto?
    
    @Transaction
    @Query("SELECT * FROM pokemon WHERE id = :id")
    suspend fun getPokemonWithDetails(id: Int): PokemonWithDetails?
    
    @Transaction
    @Query("SELECT * FROM pokemon ORDER BY id ASC")
    fun getAllPokemonWithDetails(): Flow<List<PokemonWithDetails>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: PokemonDbDto)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonTypes(types: List<PokemonTypeDbDto>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonAbilities(abilities: List<PokemonAbilityDbDto>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonStats(stats: List<PokemonStatDbDto>)
    
    @Transaction
    suspend fun insertPokemonWithDetails(
        pokemon: PokemonDbDto,
        types: List<PokemonTypeDbDto>,
        abilities: List<PokemonAbilityDbDto>,
        stats: List<PokemonStatDbDto>
    ) {
        insertPokemon(pokemon)
        insertPokemonTypes(types)
        insertPokemonAbilities(abilities)
        insertPokemonStats(stats)
    }
    
    @Query("DELETE FROM pokemon WHERE id = :id")
    suspend fun deletePokemonById(id: Int)
    
    @Query("DELETE FROM pokemon_types WHERE pokemonId = :pokemonId")
    suspend fun deletePokemonTypes(pokemonId: Int)
    
    @Query("DELETE FROM pokemon_abilities WHERE pokemonId = :pokemonId")
    suspend fun deletePokemonAbilities(pokemonId: Int)
    
    @Query("DELETE FROM pokemon_stats WHERE pokemonId = :pokemonId")
    suspend fun deletePokemonStats(pokemonId: Int)
    
    @Transaction
    suspend fun deletePokemonWithDetails(id: Int) {
        deletePokemonTypes(id)
        deletePokemonAbilities(id)
        deletePokemonStats(id)
        deletePokemonById(id)
    }
    
    @Query("DELETE FROM pokemon")
    suspend fun deleteAllPokemon()
    
    @Query("DELETE FROM pokemon_types")
    suspend fun deleteAllPokemonTypes()
    
    @Query("DELETE FROM pokemon_abilities")
    suspend fun deleteAllPokemonAbilities()
    
    @Query("DELETE FROM pokemon_stats")
    suspend fun deleteAllPokemonStats()
}
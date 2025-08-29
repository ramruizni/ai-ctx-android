package com.example.deepseekpokeappten.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.deepseekpokeappten.database.daos.PokemonDao
import com.example.deepseekpokeappten.database.entities.PokemonEntity

@Database(
    entities = [
        PokemonEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class DeepSeekPokeAppTenDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
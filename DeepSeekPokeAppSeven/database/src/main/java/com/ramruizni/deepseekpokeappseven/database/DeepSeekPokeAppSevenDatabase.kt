package com.ramruizni.deepseekpokeappseven.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.ramruizni.deepseekpokeappseven.database.entities.PokemonDao
import com.ramruizni.deepseekpokeappseven.database.entities.PokemonDbDto

@Database(
    entities = [
        PokemonDbDto::class
    ],
    version = 1,
    exportSchema = true
)
abstract class DeepSeekPokeAppSevenDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    companion object {
        @Volatile
        private var INSTANCE: DeepSeekPokeAppSevenDatabase? = null

        fun getDatabase(context: Context): DeepSeekPokeAppSevenDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DeepSeekPokeAppSevenDatabase::class.java,
                    "deepseek_poke_app_seven_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
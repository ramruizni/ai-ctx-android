package com.ramruizni.deepseekpokeappfive.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ramruizni.deepseekpokeappfive.pokemon.datasource.converters.PokemonConverters
import com.ramruizni.deepseekpokeappfive.pokemon.datasource.daos.PokemonDao
import com.ramruizni.deepseekpokeappfive.pokemon.datasource.dbdtos.PokemonDbDto

@Database(
    entities = [
        PokemonDbDto::class
    ],
    version = 1
)
@TypeConverters(PokemonConverters::class)
abstract class DeepSeekPokeAppFiveDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
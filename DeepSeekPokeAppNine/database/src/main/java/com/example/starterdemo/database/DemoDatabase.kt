package com.ramruizni.deepseekpokeappnine.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ramruizni.deepseekpokeappnine.pokemon.datasource.daos.PokemonDao
import com.ramruizni.deepseekpokeappnine.pokemon.datasource.dbdtos.PokemonDbDto

@Database(
    entities = [
        PokemonDbDto::class
    ],
    version = 1
)
abstract class DeepSeekPokeAppNineDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
package com.ramruizni.deepseekpokeappthirteen.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ramruizni.deepseekpokeappthirteen.database.daos.PokemonDatabaseDao
import com.ramruizni.deepseekpokeappthirteen.database.entities.PokemonEntity

@Database(
    entities = [
        PokemonEntity::class
    ],
    version = 1
)
abstract class DemoDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDatabaseDao
}
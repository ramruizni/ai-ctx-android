package com.example.deepseekpokeappeleven.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.deepseekpokeappeleven.pokemon.datasource.entities.PokemonEntity
import com.example.deepseekpokeappeleven.database.daos.PokemonDao
//import com.example.deepseekpokeappeleven.demo.datasource.daos.DemoDao
//import com.example.deepseekpokeappeleven.demo.datasource.dbdtos.DemoDbDto

@Database(
    entities = [
        PokemonEntity::class,
        // NEW ENTITIES GO HERE
        //DemoDbDto::class
    ],
    version = 1,
    exportSchema = true
)
abstract class DeepSeekPokeAppElevenDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    // NEW DAOS GO HERE
    //abstract fun demoDao(): DemoDao
}
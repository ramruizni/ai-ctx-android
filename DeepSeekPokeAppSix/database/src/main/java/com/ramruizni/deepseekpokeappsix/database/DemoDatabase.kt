package com.ramruizni.deepseekpokeappsix.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ramruizni.deepseekpokeappsix.database.converters.pokemon.PokemonConverters
import com.ramruizni.deepseekpokeappsix.database.daos.pokemon.PokemonDao
import com.ramruizni.deepseekpokeappsix.database.entities.pokemon.PokemonDbDto
//import com.ramruizni.deepseekpokeappsix.demo.datasource.daos.DemoDao
//import com.ramruizni.deepseekpokeappsix.demo.datasource.dbdtos.DemoDbDto

@Database(
    entities = [
        PokemonDbDto::class,
        // NEW ENTITIES GO HERE
        //DemoDbDto::class
    ],
    version = 1
)
@TypeConverters(PokemonConverters::class)
abstract class DeepSeekPokeAppSixDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    // NEW DAOS GO HERE
    //abstract fun demoDao(): DemoDao
}
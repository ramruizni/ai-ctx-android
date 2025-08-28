package com.ramruizni.deepseekpokeappfour.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ramruizni.deepseekpokeappfour.pokemon.datasource.PokemonDao
import com.ramruizni.deepseekpokeappfour.pokemon.datasource.PokemonDbDto
//import com.ramruizni.deepseekpokeappfour.demo.datasource.daos.DemoDao
//import com.ramruizni.deepseekpokeappfour.demo.datasource.dbdtos.DemoDbDto

@Database(
    entities = [
        PokemonDbDto::class
        // NEW ENTITIES GO HERE
        //DeepSeekPokeAppFourDbDto::class
    ],
    version = 1,
    exportSchema = false
)
abstract class DeepSeekPokeAppFourDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    // NEW DAOS GO HERE
    //abstract fun deepSeekPokeAppFourDao(): DeepSeekPokeAppFourDao
}
package com.ramruizni.deepseekpokeappfourteen.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ramruizni.deepseekpokeappfourteen.database.entities.PokemonEntity
import com.ramruizni.deepseekpokeappfourteen.database.daos.PokemonDao
//import com.ramruizni.deepseekpokeappfourteen.demo.datasource.daos.DemoDao
//import com.ramruizni.deepseekpokeappfourteen.demo.datasource.dbdtos.DemoDbDto

@Database(
    entities = [
        PokemonEntity::class
        // NEW ENTITIES GO HERE
        //DemoDbDto::class
    ],
    version = 1
)
@TypeConverters(PokemonTypeConverters::class)
abstract class DeepSeekPokeAppFourteenDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    // NEW DAOS GO HERE
    //abstract fun demoDao(): DemoDao
}
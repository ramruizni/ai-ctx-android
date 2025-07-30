package com.example.movies.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.movie.datasource.daos.MovieDao
import com.example.movies.movie.datasource.dbdtos.MovieDbDto
//import com.example.movies.demo.datasource.daos.DemoDao
//import com.example.movies.demo.datasource.dbdtos.DemoDbDto

@Database(
    entities = [
        // NEW ENTITIES GO HERE
        MovieDbDto::class
        //DemoDbDto::class
    ],
    version = 1
)
abstract class DemoDatabase : RoomDatabase() {
    // NEW DAOS GO HERE
    abstract fun movieDao(): MovieDao
    //abstract fun demoDao(): DemoDao
}
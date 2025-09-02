package com.example.todopapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todopapp.database.converters.DateTimeConverter
import com.example.todopapp.database.daos.TodoDao
import com.example.todopapp.database.entities.TodoEntity

@Database(
    entities = [
        TodoEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateTimeConverter::class)
abstract class DemoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}
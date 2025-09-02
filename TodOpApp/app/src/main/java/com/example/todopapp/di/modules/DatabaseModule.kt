package com.example.todopapp.di.modules

import android.content.Context
import androidx.room.Room
import com.example.todopapp.database.DemoDatabase
import com.example.todopapp.database.daos.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): DemoDatabase = Room.databaseBuilder(
        context,
        DemoDatabase::class.java,
        "todo_database"
    ).build()

    @Provides
    fun provideTodoDao(database: DemoDatabase): TodoDao = database.todoDao()
}
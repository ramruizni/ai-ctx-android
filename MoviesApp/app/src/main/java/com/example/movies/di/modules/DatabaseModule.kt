package com.example.movies.di.modules

import android.content.Context
import androidx.room.Room
import com.example.movies.database.DemoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDemoDatabase(
        @ApplicationContext context: Context
    ): DemoDatabase {
        return Room.databaseBuilder(
            context,
            DemoDatabase::class.java,
            "movies_database"
        ).build()
    }
}
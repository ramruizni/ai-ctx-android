package com.ramruizni.deepseekpokeappthirteen.di.modules

import android.content.Context
import androidx.room.Room
import com.ramruizni.deepseekpokeappthirteen.database.DemoDatabase
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
    fun provideDemoDatabase(@ApplicationContext context: Context): DemoDatabase {
        return Room.databaseBuilder(
            context,
            DemoDatabase::class.java,
            "demo_database"
        ).fallbackToDestructiveMigration()
            .build()
    }
}
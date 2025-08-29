package com.ramruizni.deepseekpokeappeight.di.modules

import android.content.Context
import androidx.room.Room
import com.ramruizni.deepseekpokeappeight.database.DeepSeekPokeAppEightDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module providing database dependencies for the entire application.
 * Provides the main Room database instance used by all feature modules.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    private const val DATABASE_NAME = "deepseek_poke_app_eight_database"
    
    /**
     * Provides the main application database instance
     * @param context Application context for database creation
     * @return DeepSeekPokeAppEightDatabase singleton instance
     */
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DeepSeekPokeAppEightDatabase {
        return Room.databaseBuilder(
            context,
            DeepSeekPokeAppEightDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration() // For development - remove in production
            .build()
    }
}
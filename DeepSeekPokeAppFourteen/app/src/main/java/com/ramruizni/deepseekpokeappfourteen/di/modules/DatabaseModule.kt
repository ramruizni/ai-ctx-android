package com.ramruizni.deepseekpokeappfourteen.di.modules

import android.content.Context
import androidx.room.Room
import com.ramruizni.deepseekpokeappfourteen.database.DeepSeekPokeAppFourteenDatabase
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
    fun provideDatabase(@ApplicationContext context: Context): DeepSeekPokeAppFourteenDatabase {
        return Room.databaseBuilder(
            context,
            DeepSeekPokeAppFourteenDatabase::class.java,
            "deepseekpokeappfourteen_database"
        ).build()
    }
}
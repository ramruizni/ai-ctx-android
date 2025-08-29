package com.example.deepseekpokeappten.di.modules

import android.content.Context
import androidx.room.Room
import com.example.deepseekpokeappten.database.DeepSeekPokeAppTenDatabase
import com.example.deepseekpokeappten.database.daos.PokemonDao
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
    fun provideDatabase(@ApplicationContext context: Context): DeepSeekPokeAppTenDatabase {
        return Room.databaseBuilder(
            context,
            DeepSeekPokeAppTenDatabase::class.java,
            "deepseekpokeappten_database"
        ).build()
    }

    @Provides
    fun providePokemonDao(database: DeepSeekPokeAppTenDatabase): PokemonDao {
        return database.pokemonDao()
    }
}
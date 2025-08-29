package com.example.deepseekpokeappeleven.di.modules

import android.content.Context
import androidx.room.Room
import com.example.deepseekpokeappeleven.database.DeepSeekPokeAppElevenDatabase
import com.example.deepseekpokeappeleven.database.daos.PokemonDao
import com.example.deepseekpokeappeleven.database.PokemonLocalDataSourceImpl
import com.example.deepseekpokeappeleven.pokemon.datasource.local.PokemonLocalDataSource
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
    fun providesDatabase(@ApplicationContext context: Context): DeepSeekPokeAppElevenDatabase {
        return Room.databaseBuilder(
            context,
            DeepSeekPokeAppElevenDatabase::class.java,
            "deepseek_poke_app_eleven_database"
        ).apply {
            fallbackToDestructiveMigration() // For development
        }.build()
    }

    @Provides
    @Singleton
    fun providesPokemonDao(database: DeepSeekPokeAppElevenDatabase): PokemonDao {
        return database.pokemonDao()
    }

    @Provides
    @Singleton
    fun providesPokemonLocalDataSource(pokemonDao: PokemonDao): PokemonLocalDataSource {
        return PokemonLocalDataSourceImpl(pokemonDao)
    }
}
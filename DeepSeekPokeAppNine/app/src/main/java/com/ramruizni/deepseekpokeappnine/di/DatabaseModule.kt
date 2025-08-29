package com.ramruizni.deepseekpokeappnine.di

import android.content.Context
import androidx.room.Room
import com.ramruizni.deepseekpokeappnine.database.DeepSeekPokeAppNineDatabase
import com.ramruizni.deepseekpokeappnine.pokemon.datasource.daos.PokemonDao
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
    fun provideDatabase(@ApplicationContext context: Context): DeepSeekPokeAppNineDatabase {
        return Room.databaseBuilder(
            context,
            DeepSeekPokeAppNineDatabase::class.java,
            "deepseek_poke_app_nine_database"
        ).build()
    }

    @Provides
    fun providePokemonDao(database: DeepSeekPokeAppNineDatabase): PokemonDao {
        return database.pokemonDao()
    }
}
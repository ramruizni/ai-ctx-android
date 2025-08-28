package com.ramruizni.deepseekpokeappfour.di.modules

import android.content.Context
import androidx.room.Room
import com.ramruizni.deepseekpokeappfour.database.DeepSeekPokeAppFourDatabase
import com.ramruizni.deepseekpokeappfour.pokemon.datasource.PokemonDao
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
    fun providesDeepSeekPokeAppFourDatabase(
        @ApplicationContext context: Context
    ): DeepSeekPokeAppFourDatabase = Room.databaseBuilder(
        context,
        DeepSeekPokeAppFourDatabase::class.java,
        "deep_seek_poke_app_four_database"
    ).build()
    
    @Provides
    fun providesPokemonDao(
        database: DeepSeekPokeAppFourDatabase
    ): PokemonDao = database.pokemonDao()
}
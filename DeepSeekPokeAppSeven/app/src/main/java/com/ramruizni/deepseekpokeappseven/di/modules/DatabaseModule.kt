package com.ramruizni.deepseekpokeappseven.di.modules

import android.content.Context
import com.ramruizni.deepseekpokeappseven.database.DeepSeekPokeAppSevenDatabase
import com.ramruizni.deepseekpokeappseven.database.entities.PokemonDao
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
    fun provideDatabase(@ApplicationContext context: Context): DeepSeekPokeAppSevenDatabase {
        return DeepSeekPokeAppSevenDatabase.getDatabase(context)
    }
    
    @Provides
    fun providePokemonDao(database: DeepSeekPokeAppSevenDatabase): PokemonDao {
        return database.pokemonDao()
    }
}
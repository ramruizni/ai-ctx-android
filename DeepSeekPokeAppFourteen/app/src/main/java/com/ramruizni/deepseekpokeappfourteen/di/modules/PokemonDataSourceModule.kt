package com.ramruizni.deepseekpokeappfourteen.di.modules

import com.ramruizni.deepseekpokeappfourteen.database.DeepSeekPokeAppFourteenDatabase
import com.ramruizni.deepseekpokeappfourteen.pokemon.datasource.PokemonDataSource
import com.ramruizni.deepseekpokeappfourteen.pokemon.datasource.PokemonDataSourceImpl
import com.ramruizni.deepseekpokeappfourteen.database.daos.PokemonDao
import com.ramruizni.deepseekpokeappfourteen.pokemon.datasource.remote.PokemonApiService
import com.ramruizni.deepseekpokeappfourteen.pokemon.datasource.remote.PokemonRemoteDataSource
import com.ramruizni.deepseekpokeappfourteen.pokemon.datasource.remote.PokemonRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonDataSourceModule {

    @Provides
    fun providePokemonDao(database: DeepSeekPokeAppFourteenDatabase): PokemonDao {
        return database.pokemonDao()
    }

    @Singleton
    @Provides
    fun providePokemonDataSource(dao: PokemonDao): PokemonDataSource {
        return PokemonDataSourceImpl(dao)
    }

    @Provides
    fun providePokemonApiService(retrofit: Retrofit): PokemonApiService {
        return retrofit.create(PokemonApiService::class.java)
    }

    @Singleton
    @Provides
    fun providePokemonRemoteDataSource(apiService: PokemonApiService): PokemonRemoteDataSource {
        return PokemonRemoteDataSourceImpl(apiService)
    }
}
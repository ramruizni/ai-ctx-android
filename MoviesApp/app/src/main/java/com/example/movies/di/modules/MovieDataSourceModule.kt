package com.example.movies.di.modules

import com.example.movies.database.DemoDatabase
import com.example.movies.movie.datasource.MoviesDataSourceImpl
import com.example.movies.movie.datasource.api.TMDbApiService
import com.example.movies.movie.datasource.daos.MovieDao
import com.example.movies.movie.infrastructure.MoviesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDataSourceModule {
    
    @Provides
    @Singleton
    fun provideMovieDao(database: DemoDatabase): MovieDao {
        return database.movieDao()
    }
    
    @Provides
    @Singleton
    fun provideMoviesDataSource(
        movieDao: MovieDao,
        apiService: TMDbApiService
    ): MoviesDataSource {
        return MoviesDataSourceImpl(movieDao, apiService)
    }
}
package com.example.movies.di.modules

import com.example.movies.movie.domain.MoviesRepository
import com.example.movies.movie.infrastructure.MoviesDataSource
import com.example.movies.movie.infrastructure.MoviesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieInfrastructureModule {
    
    @Provides
    @Singleton
    fun provideMoviesRepository(moviesDataSource: MoviesDataSource): MoviesRepository {
        return MoviesRepositoryImpl(moviesDataSource)
    }
}
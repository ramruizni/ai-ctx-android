package com.example.movies.di.modules

import com.example.movies.movie.domain.MoviesRepository
import com.example.movies.movie.domain.usecases.FetchMoviesUseCase
import com.example.movies.movie.domain.usecases.GetAllMoviesUseCase
import com.example.movies.movie.domain.usecases.GetMovieByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDomainModule {
    
    @Provides
    @Singleton
    fun provideGetAllMoviesUseCase(moviesRepository: MoviesRepository): GetAllMoviesUseCase {
        return GetAllMoviesUseCase(moviesRepository)
    }
    
    @Provides
    @Singleton
    fun provideFetchMoviesUseCase(moviesRepository: MoviesRepository): FetchMoviesUseCase {
        return FetchMoviesUseCase(moviesRepository)
    }
    
    @Provides
    @Singleton
    fun provideGetMovieByIdUseCase(moviesRepository: MoviesRepository): GetMovieByIdUseCase {
        return GetMovieByIdUseCase(moviesRepository)
    }
}
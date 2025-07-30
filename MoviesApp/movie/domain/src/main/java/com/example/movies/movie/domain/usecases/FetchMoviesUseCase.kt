package com.example.movies.movie.domain.usecases

import com.example.movies.movie.domain.MoviesRepository
import javax.inject.Inject

class FetchMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    
    suspend fun fetchPopularMovies(): Result<Unit> {
        return repository.fetchAndStorePopularMovies()
    }
    
    suspend fun fetchTopRatedMovies(): Result<Unit> {
        return repository.fetchAndStoreTopRatedMovies()
    }
    
    suspend fun fetchNowPlayingMovies(): Result<Unit> {
        return repository.fetchAndStoreNowPlayingMovies()
    }
}
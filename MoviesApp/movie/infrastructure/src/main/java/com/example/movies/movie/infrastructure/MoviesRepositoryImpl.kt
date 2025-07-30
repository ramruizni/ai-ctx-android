package com.example.movies.movie.infrastructure

import com.example.movies.movie.domain.MoviesRepository
import com.example.movies.movie.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesDataSource: MoviesDataSource
) : MoviesRepository {
    
    override fun getAllMovies(): Flow<List<Movie>> {
        return moviesDataSource.getAllMovies()
    }
    
    override fun getMovieById(id: Int): Flow<Movie?> {
        return moviesDataSource.getMovieById(id)
    }
    
    override suspend fun fetchAndStorePopularMovies(): Result<Unit> {
        return moviesDataSource.fetchAndStorePopularMovies()
    }
    
    override suspend fun fetchAndStoreTopRatedMovies(): Result<Unit> {
        return moviesDataSource.fetchAndStoreTopRatedMovies()
    }
    
    override suspend fun fetchAndStoreNowPlayingMovies(): Result<Unit> {
        return moviesDataSource.fetchAndStoreNowPlayingMovies()
    }
}
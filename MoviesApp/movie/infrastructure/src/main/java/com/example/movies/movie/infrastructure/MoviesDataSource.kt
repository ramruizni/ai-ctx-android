package com.example.movies.movie.infrastructure

import com.example.movies.movie.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesDataSource {
    fun getAllMovies(): Flow<List<Movie>>
    fun getMovieById(id: Int): Flow<Movie?>
    suspend fun fetchAndStorePopularMovies(): Result<Unit>
    suspend fun fetchAndStoreTopRatedMovies(): Result<Unit>
    suspend fun fetchAndStoreNowPlayingMovies(): Result<Unit>
}
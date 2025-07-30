package com.example.movies.movie.datasource

import com.example.movies.movie.datasource.api.TMDbApiService
import com.example.movies.movie.datasource.daos.MovieDao
import com.example.movies.movie.datasource.dbdtos.toDomain
import com.example.movies.movie.datasource.mappers.toDbDto
import com.example.movies.movie.domain.models.Movie
import com.example.movies.movie.infrastructure.MoviesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val apiService: TMDbApiService
) : MoviesDataSource {
    
    companion object {
        private const val API_KEY = "86fc036c1f41dca5ee71aa7fb3c327cf"
    }
    
    override fun getAllMovies(): Flow<List<Movie>> {
        return movieDao.getAllMovies().map { movieDbDtos ->
            movieDbDtos.map { it.toDomain() }
        }
    }
    
    override fun getMovieById(id: Int): Flow<Movie?> {
        return movieDao.getMovieById(id).map { movieDbDto ->
            movieDbDto?.toDomain()
        }
    }
    
    override suspend fun fetchAndStorePopularMovies(): Result<Unit> {
        return try {
            val response = apiService.getPopularMovies(API_KEY)
            val movieDbDtos = response.results.map { it.toDbDto() }
            movieDao.insertMovies(movieDbDtos)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun fetchAndStoreTopRatedMovies(): Result<Unit> {
        return try {
            val response = apiService.getTopRatedMovies(API_KEY)
            val movieDbDtos = response.results.map { it.toDbDto() }
            movieDao.insertMovies(movieDbDtos)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun fetchAndStoreNowPlayingMovies(): Result<Unit> {
        return try {
            val response = apiService.getNowPlayingMovies(API_KEY)
            val movieDbDtos = response.results.map { it.toDbDto() }
            movieDao.insertMovies(movieDbDtos)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
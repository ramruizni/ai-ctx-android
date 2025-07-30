package com.example.movies.features.movies.viewmodel

import com.example.movies.movie.domain.models.Movie

sealed class MoviesUiState {
    data object Loading : MoviesUiState()
    data class Success(val movies: List<Movie>) : MoviesUiState()
    data class Error(val message: String) : MoviesUiState()
}
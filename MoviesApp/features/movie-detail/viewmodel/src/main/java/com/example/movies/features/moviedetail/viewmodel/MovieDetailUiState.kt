package com.example.movies.features.moviedetail.viewmodel

import com.example.movies.movie.domain.models.Movie

sealed class MovieDetailUiState {
    data object Loading : MovieDetailUiState()
    data class Success(val movie: Movie) : MovieDetailUiState()
    data class Error(val message: String) : MovieDetailUiState()
    data object NotFound : MovieDetailUiState()
}
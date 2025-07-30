package com.example.movies.features.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.movie.domain.usecases.FetchMoviesUseCase
import com.example.movies.movie.domain.usecases.GetAllMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val fetchMoviesUseCase: FetchMoviesUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<MoviesUiState>(MoviesUiState.Loading)
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()
    
    init {
        loadMovies()
        fetchPopularMovies()
    }
    
    private fun loadMovies() {
        viewModelScope.launch {
            getAllMoviesUseCase()
                .catch { throwable ->
                    _uiState.value = MoviesUiState.Error(
                        message = throwable.message ?: "Unknown error occurred"
                    )
                }
                .collect { movies ->
                    _uiState.value = MoviesUiState.Success(movies = movies)
                }
        }
    }
    
    fun fetchPopularMovies() {
        viewModelScope.launch {
            fetchMoviesUseCase.fetchPopularMovies()
                .onFailure { error ->
                    _uiState.value = MoviesUiState.Error(
                        message = error.message ?: "Failed to fetch movies"
                    )
                }
        }
    }
    
    fun fetchTopRatedMovies() {
        viewModelScope.launch {
            fetchMoviesUseCase.fetchTopRatedMovies()
                .onFailure { error ->
                    _uiState.value = MoviesUiState.Error(
                        message = error.message ?: "Failed to fetch movies"
                    )
                }
        }
    }
    
    fun fetchNowPlayingMovies() {
        viewModelScope.launch {
            fetchMoviesUseCase.fetchNowPlayingMovies()
                .onFailure { error ->
                    _uiState.value = MoviesUiState.Error(
                        message = error.message ?: "Failed to fetch movies"
                    )
                }
        }
    }
    
    fun refresh() {
        _uiState.value = MoviesUiState.Loading
        fetchPopularMovies()
    }
}
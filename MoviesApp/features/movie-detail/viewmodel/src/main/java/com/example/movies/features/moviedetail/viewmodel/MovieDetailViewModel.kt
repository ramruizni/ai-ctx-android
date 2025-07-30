package com.example.movies.features.moviedetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.movie.domain.usecases.GetMovieByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieByIdUseCase: GetMovieByIdUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Loading)
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()
    
    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = MovieDetailUiState.Loading
            
            getMovieByIdUseCase(movieId)
                .catch { throwable ->
                    _uiState.value = MovieDetailUiState.Error(
                        throwable.message ?: "Unknown error occurred"
                    )
                }
                .collect { movie ->
                    if (movie != null) {
                        _uiState.value = MovieDetailUiState.Success(movie)
                    } else {
                        _uiState.value = MovieDetailUiState.NotFound
                    }
                }
        }
    }
}
package com.example.todopapp.features.todo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todopapp.todo.domain.models.Todo
import com.example.todopapp.todo.domain.usecases.AddTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val addTodoUseCase: AddTodoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddEditTodoUiState())
    val uiState: StateFlow<AddEditTodoUiState> = _uiState.asStateFlow()

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun updateDescription(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun saveTodo() {
        val currentState = _uiState.value
        
        if (currentState.title.isBlank()) {
            _uiState.value = currentState.copy(errorMessage = "Title cannot be empty")
            return
        }

        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true, errorMessage = null)
            
            try {
                val todo = Todo(
                    title = currentState.title.trim(),
                    description = currentState.description.trim()
                )
                
                addTodoUseCase(todo)
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSaved = true
                )
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = exception.message
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
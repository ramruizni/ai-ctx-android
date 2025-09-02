package com.example.todopapp.features.todo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todopapp.todo.domain.models.Todo
import com.example.todopapp.todo.domain.usecases.DeleteTodoUseCase
import com.example.todopapp.todo.domain.usecases.GetAllTodosUseCase
import com.example.todopapp.todo.domain.usecases.ToggleTodoCompletionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val getAllTodosUseCase: GetAllTodosUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val toggleTodoCompletionUseCase: ToggleTodoCompletionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoUiState())
    val uiState: StateFlow<TodoUiState> = _uiState.asStateFlow()

    init {
        loadTodos()
    }

    private fun loadTodos() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            getAllTodosUseCase()
                .catch { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message
                    )
                }
                .collect { todos ->
                    _uiState.value = _uiState.value.copy(
                        todos = todos,
                        isLoading = false,
                        errorMessage = null
                    )
                }
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            try {
                deleteTodoUseCase(todo)
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = exception.message
                )
            }
        }
    }

    fun toggleTodoCompletion(id: Long) {
        viewModelScope.launch {
            try {
                toggleTodoCompletionUseCase(id)
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = exception.message
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
package com.example.todopapp.features.todo.viewmodel

import com.example.todopapp.todo.domain.models.Todo

data class TodoUiState(
    val todos: List<Todo> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class AddEditTodoUiState(
    val title: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSaved: Boolean = false
)
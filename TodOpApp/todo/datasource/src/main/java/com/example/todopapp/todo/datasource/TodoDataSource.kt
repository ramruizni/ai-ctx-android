package com.example.todopapp.todo.datasource

import com.example.todopapp.todo.datasource.dbdtos.TodoDbDto
import kotlinx.coroutines.flow.Flow

interface TodoDataSource {
    fun getAllTodos(): Flow<List<TodoDbDto>>
    suspend fun getTodoById(id: Long): TodoDbDto?
    suspend fun insertTodo(todo: TodoDbDto): Long
    suspend fun updateTodo(todo: TodoDbDto)
    suspend fun deleteTodo(todo: TodoDbDto)
    suspend fun toggleTodoCompletion(id: Long)
}
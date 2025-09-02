package com.example.todopapp.todo.domain

import com.example.todopapp.todo.domain.models.Todo
import kotlinx.coroutines.flow.Flow

interface TodosRepository {
    fun getAllTodos(): Flow<List<Todo>>
    suspend fun getTodoById(id: Long): Todo?
    suspend fun insertTodo(todo: Todo): Long
    suspend fun updateTodo(todo: Todo)
    suspend fun deleteTodo(todo: Todo)
    suspend fun toggleTodoCompletion(id: Long)
}
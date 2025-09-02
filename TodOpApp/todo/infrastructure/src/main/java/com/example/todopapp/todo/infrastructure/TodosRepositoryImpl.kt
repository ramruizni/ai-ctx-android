package com.example.todopapp.todo.infrastructure

import com.example.todopapp.todo.datasource.TodoDataSource
import com.example.todopapp.todo.domain.TodosRepository
import com.example.todopapp.todo.domain.models.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodosRepositoryImpl(
    private val todoDataSource: TodoDataSource
) : TodosRepository {

    override fun getAllTodos(): Flow<List<Todo>> {
        return todoDataSource.getAllTodos().map { todos ->
            todos.map { it.toDomain() }
        }
    }

    override suspend fun getTodoById(id: Long): Todo? {
        return todoDataSource.getTodoById(id)?.toDomain()
    }

    override suspend fun insertTodo(todo: Todo): Long {
        return todoDataSource.insertTodo(todo.toDbDto())
    }

    override suspend fun updateTodo(todo: Todo) {
        todoDataSource.updateTodo(todo.toDbDto())
    }

    override suspend fun deleteTodo(todo: Todo) {
        todoDataSource.deleteTodo(todo.toDbDto())
    }

    override suspend fun toggleTodoCompletion(id: Long) {
        todoDataSource.toggleTodoCompletion(id)
    }
}
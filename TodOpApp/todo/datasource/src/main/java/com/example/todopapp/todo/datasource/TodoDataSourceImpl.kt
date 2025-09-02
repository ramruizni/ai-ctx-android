package com.example.todopapp.todo.datasource

import com.example.todopapp.database.daos.TodoDao
import com.example.todopapp.database.entities.TodoEntity
import com.example.todopapp.todo.datasource.dbdtos.TodoDbDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoDataSourceImpl(
    private val todoDao: TodoDao
) : TodoDataSource {
    
    override fun getAllTodos(): Flow<List<TodoDbDto>> {
        return todoDao.getAllTodos().map { entities ->
            entities.map { it.toDbDto() }
        }
    }

    override suspend fun getTodoById(id: Long): TodoDbDto? {
        return todoDao.getTodoById(id)?.toDbDto()
    }

    override suspend fun insertTodo(todo: TodoDbDto): Long {
        return todoDao.insertTodo(todo.toEntity())
    }

    override suspend fun updateTodo(todo: TodoDbDto) {
        return todoDao.updateTodo(todo.toEntity())
    }

    override suspend fun deleteTodo(todo: TodoDbDto) {
        return todoDao.deleteTodo(todo.toEntity())
    }

    override suspend fun toggleTodoCompletion(id: Long) {
        return todoDao.toggleTodoCompletion(id)
    }
}

private fun TodoEntity.toDbDto(): TodoDbDto = TodoDbDto(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
    createdAt = createdAt
)

private fun TodoDbDto.toEntity(): TodoEntity = TodoEntity(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
    createdAt = createdAt
)
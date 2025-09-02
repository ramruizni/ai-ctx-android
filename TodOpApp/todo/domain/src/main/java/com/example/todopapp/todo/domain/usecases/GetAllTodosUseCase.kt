package com.example.todopapp.todo.domain.usecases

import com.example.todopapp.todo.domain.TodosRepository
import com.example.todopapp.todo.domain.models.Todo
import kotlinx.coroutines.flow.Flow

class GetAllTodosUseCase(
    private val todosRepository: TodosRepository
) {
    operator fun invoke(): Flow<List<Todo>> {
        return todosRepository.getAllTodos()
    }
}
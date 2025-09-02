package com.example.todopapp.todo.domain.usecases

import com.example.todopapp.todo.domain.TodosRepository

class ToggleTodoCompletionUseCase(
    private val todosRepository: TodosRepository
) {
    suspend operator fun invoke(id: Long) {
        return todosRepository.toggleTodoCompletion(id)
    }
}
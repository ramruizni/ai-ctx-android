package com.example.todopapp.todo.domain.usecases

import com.example.todopapp.todo.domain.TodosRepository
import com.example.todopapp.todo.domain.models.Todo

class DeleteTodoUseCase(
    private val todosRepository: TodosRepository
) {
    suspend operator fun invoke(todo: Todo) {
        return todosRepository.deleteTodo(todo)
    }
}
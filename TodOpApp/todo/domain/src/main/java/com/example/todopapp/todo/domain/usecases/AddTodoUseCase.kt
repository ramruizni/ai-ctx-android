package com.example.todopapp.todo.domain.usecases

import com.example.todopapp.todo.domain.TodosRepository
import com.example.todopapp.todo.domain.models.Todo

class AddTodoUseCase(
    private val todosRepository: TodosRepository
) {
    suspend operator fun invoke(todo: Todo): Long {
        return todosRepository.insertTodo(todo)
    }
}
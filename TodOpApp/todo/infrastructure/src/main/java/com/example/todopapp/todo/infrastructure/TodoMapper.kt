package com.example.todopapp.todo.infrastructure

import com.example.todopapp.todo.datasource.dbdtos.TodoDbDto
import com.example.todopapp.todo.domain.models.Todo

fun TodoDbDto.toDomain(): Todo = Todo(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
    createdAt = createdAt
)

fun Todo.toDbDto(): TodoDbDto = TodoDbDto(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted,
    createdAt = createdAt
)
package com.example.todopapp.todo.domain.models

import java.time.LocalDateTime

data class Todo(
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
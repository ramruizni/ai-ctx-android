package com.example.todopapp.todo.datasource.dbdtos

import java.time.LocalDateTime

data class TodoDbDto(
    val id: Long = 0,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val createdAt: LocalDateTime
)
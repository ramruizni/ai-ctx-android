package com.example.todopapp.di.modules

import com.example.todopapp.todo.domain.TodosRepository
import com.example.todopapp.todo.domain.usecases.AddTodoUseCase
import com.example.todopapp.todo.domain.usecases.DeleteTodoUseCase
import com.example.todopapp.todo.domain.usecases.GetAllTodosUseCase
import com.example.todopapp.todo.domain.usecases.ToggleTodoCompletionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoDomainModule {

    @Singleton
    @Provides
    fun provideGetAllTodosUseCase(
        todosRepository: TodosRepository
    ): GetAllTodosUseCase = GetAllTodosUseCase(todosRepository)

    @Singleton
    @Provides
    fun provideAddTodoUseCase(
        todosRepository: TodosRepository
    ): AddTodoUseCase = AddTodoUseCase(todosRepository)

    @Singleton
    @Provides
    fun provideDeleteTodoUseCase(
        todosRepository: TodosRepository
    ): DeleteTodoUseCase = DeleteTodoUseCase(todosRepository)

    @Singleton
    @Provides
    fun provideToggleTodoCompletionUseCase(
        todosRepository: TodosRepository
    ): ToggleTodoCompletionUseCase = ToggleTodoCompletionUseCase(todosRepository)
}
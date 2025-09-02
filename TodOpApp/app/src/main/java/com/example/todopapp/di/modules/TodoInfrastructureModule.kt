package com.example.todopapp.di.modules

import com.example.todopapp.todo.datasource.TodoDataSource
import com.example.todopapp.todo.domain.TodosRepository
import com.example.todopapp.todo.infrastructure.TodosRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoInfrastructureModule {

    @Singleton
    @Provides
    fun provideTodosRepository(
        todoDataSource: TodoDataSource
    ): TodosRepository = TodosRepositoryImpl(todoDataSource)
}
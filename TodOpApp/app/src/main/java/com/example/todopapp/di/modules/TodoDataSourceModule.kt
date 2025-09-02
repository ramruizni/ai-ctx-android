package com.example.todopapp.di.modules

import com.example.todopapp.todo.datasource.TodoDataSource
import com.example.todopapp.todo.datasource.TodoDataSourceImpl
import com.example.todopapp.database.daos.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoDataSourceModule {

    @Singleton
    @Provides
    fun provideTodoDataSource(
        todoDao: TodoDao
    ): TodoDataSource = TodoDataSourceImpl(todoDao)
}
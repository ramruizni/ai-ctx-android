package com.example.todopapp.navigation.todo

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.todopapp.features.todo.view.AddEditTodoScreen
import com.example.todopapp.features.todo.view.TodoListScreen
import com.example.todopapp.navigation.todo.routes.AddEditTodoRoute
import com.example.todopapp.navigation.todo.routes.TodoGraphRoute
import com.example.todopapp.navigation.todo.routes.TodoListRoute

fun NavGraphBuilder.todoGraph(navController: NavController) {
    navigation<TodoGraphRoute>(
        startDestination = TodoListRoute
    ) {
        composable<TodoListRoute> {
            TodoListScreen(
                onNavigateToAddTodo = {
                    navController.navigate(AddEditTodoRoute)
                }
            )
        }
        
        composable<AddEditTodoRoute> {
            AddEditTodoScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
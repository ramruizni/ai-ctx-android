package com.example.todopapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.todopapp.navigation.todo.todoGraph
import com.example.todopapp.navigation.todo.routes.TodoGraphRoute

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    NavHost(
        route = RootGraphRoute::class,
        navController = navController,
        startDestination = TodoGraphRoute
    ) {
        todoGraph(navController = navController)
    }
}
package com.example.movies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.movies.navigation.movies.moviesGraph
import com.example.movies.navigation.movies.routes.MoviesGraphRoute
//import com.example.movies.navigation.demo.demoGraph
//import com.example.movies.navigation.demo.routes.DemoGraphRoute

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    NavHost(
        route = RootGraphRoute::class,
        navController = navController,
        // FIRST NAVIGATION ROUTE GOES HERE
        startDestination = MoviesGraphRoute
//        startDestination = DemoGraphRoute
    ) {
        // NAVIGATION GRAPHS GO HERE
        moviesGraph(navController = navController)
//        demoGraph(navController = navController)
    }
}
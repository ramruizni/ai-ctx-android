package com.example.movieapptwo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
//import com.example.movieapptwo.navigation.demo.demoGraph
//import com.example.movieapptwo.navigation.demo.routes.DemoGraphRoute

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    /*NavHost(
        route = RootGraphRoute::class,
        navController = navController,
        // FIRST NAVIGATION ROUTE GOES HERE
//        startDestination = DemoGraphRoute
    ) {
        // NAVIGATION GRAPHS GO HERE
//        demoGraph(navController = navController)
    }*/
}
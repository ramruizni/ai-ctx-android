package com.example.movies.navigation.movies

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.movies.features.movies.view.MoviesScreen
import com.example.movies.features.moviedetail.view.MovieDetailScreen
import com.example.movies.navigation.movies.routes.MovieDetailRoute
import com.example.movies.navigation.movies.routes.MoviesGraphRoute
import com.example.movies.navigation.movies.routes.MoviesRoute

fun NavGraphBuilder.moviesGraph(navController: NavController) {
    navigation<MoviesGraphRoute>(
        startDestination = MoviesRoute
    ) {
        composable<MoviesRoute> {
            MoviesScreen(
                onMovieClick = { movieId ->
                    navController.navigate(MovieDetailRoute(movieId))
                }
            )
        }
        
        composable<MovieDetailRoute> { backStackEntry ->
            val route = backStackEntry.arguments?.let { 
                MovieDetailRoute(it.getInt("movieId"))
            } ?: return@composable
            
            MovieDetailScreen(
                movieId = route.movieId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
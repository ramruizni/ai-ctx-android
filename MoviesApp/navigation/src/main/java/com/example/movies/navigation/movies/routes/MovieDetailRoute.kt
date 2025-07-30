package com.example.movies.navigation.movies.routes

import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailRoute(
    val movieId: Int
)
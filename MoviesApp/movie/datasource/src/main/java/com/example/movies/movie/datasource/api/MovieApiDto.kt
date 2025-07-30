package com.example.movies.movie.datasource.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieApiDto(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("overview") val overview: String,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("vote_average") val voteAverage: Float
)

@Serializable
data class MoviesApiResponse(
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<MovieApiDto>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)
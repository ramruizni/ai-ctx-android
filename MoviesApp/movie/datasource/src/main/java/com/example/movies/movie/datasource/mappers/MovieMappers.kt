package com.example.movies.movie.datasource.mappers

import com.example.movies.movie.datasource.api.MovieApiDto
import com.example.movies.movie.datasource.dbdtos.MovieDbDto
import com.example.movies.movie.domain.models.Movie

fun MovieApiDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath ?: "",
        releaseDate = releaseDate,
        rating = voteAverage
    )
}

fun MovieApiDto.toDbDto(): MovieDbDto {
    return MovieDbDto(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath ?: "",
        releaseDate = releaseDate,
        rating = voteAverage
    )
}

fun MovieDbDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        rating = rating
    )
}

fun Movie.toDbDto(): MovieDbDto {
    return MovieDbDto(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        rating = rating
    )
}
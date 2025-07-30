package com.example.movies.movie.datasource.dbdtos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movies.movie.domain.models.Movie

@Entity(tableName = "movies")
data class MovieDbDto(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val rating: Float
)

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
package com.ramruizni.deepseekpokeappthirteen.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    val height: Int,
    val weight: Int,
    @ColumnInfo(name = "base_experience")
    val baseExperience: Int,
    val types: String, // JSON string
    val abilities: String, // JSON string
    val stats: String // JSON string
)
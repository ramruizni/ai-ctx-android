package com.example.deepseekpokeappten.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: String, // JSON string of types
    val sprites: String, // JSON string of sprites
    val abilities: String, // JSON string of abilities
    val stats: String // JSON string of stats
)
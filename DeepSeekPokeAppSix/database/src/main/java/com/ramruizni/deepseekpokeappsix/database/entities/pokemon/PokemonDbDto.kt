package com.ramruizni.deepseekpokeappsix.database.entities.pokemon

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ramruizni.deepseekpokeappsix.database.converters.pokemon.PokemonConverters

@Entity(tableName = "pokemon")
@TypeConverters(PokemonConverters::class)
data class PokemonDbDto(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
    val types: String, // Comma-separated
    val height: Int,
    val weight: Int,
    val abilities: String, // Comma-separated
    val stats: String // Key:value pairs separated by commas
)
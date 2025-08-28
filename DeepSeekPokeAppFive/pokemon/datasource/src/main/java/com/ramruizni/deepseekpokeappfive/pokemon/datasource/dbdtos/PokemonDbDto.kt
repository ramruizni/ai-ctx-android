package com.ramruizni.deepseekpokeappfive.pokemon.datasource.dbdtos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonDbDto(
    @PrimaryKey val id: Int,
    val name: String,
    val number: Int,
    val imageUrl: String,
    val types: String, // Comma-separated list of types
    val abilities: String, // JSON string of abilities
    val stats: String // JSON string of stats
)
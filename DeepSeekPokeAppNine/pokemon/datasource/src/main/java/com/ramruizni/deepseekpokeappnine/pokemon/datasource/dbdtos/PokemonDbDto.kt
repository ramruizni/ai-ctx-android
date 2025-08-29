package com.ramruizni.deepseekpokeappnine.pokemon.datasource.dbdtos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonDbDto(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: String, // JSON string of types
    val abilities: String, // JSON string of abilities
    val stats: String, // JSON string of stats
    val height: Int,
    val weight: Int
)

data class PokemonStatDbDto(
    val name: String,
    val baseStat: Int,
    val effort: Int
)
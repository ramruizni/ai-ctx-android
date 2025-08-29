package com.example.deepseekpokeapptwelve.pokemon.datasource.dbdtos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonDbDto(
    @PrimaryKey val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val baseExperience: Int,
    val typesJson: String,
    val abilitiesJson: String,
    val statsJson: String,
    val spritesJson: String
)
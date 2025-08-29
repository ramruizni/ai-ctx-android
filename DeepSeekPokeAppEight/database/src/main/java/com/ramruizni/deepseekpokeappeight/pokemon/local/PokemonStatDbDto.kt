package com.ramruizni.deepseekpokeappeight.pokemon.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_stats")
data class PokemonStatDbDto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pokemonId: Int,
    val statName: String,
    val baseStat: Int,
    val effort: Int
)
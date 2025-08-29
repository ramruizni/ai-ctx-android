package com.ramruizni.deepseekpokeappeight.pokemon.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_types")
data class PokemonTypeDbDto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pokemonId: Int,
    val slot: Int,
    val typeName: String
)
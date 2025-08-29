package com.ramruizni.deepseekpokeappeight.pokemon.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonDbDto(
    @PrimaryKey val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val baseExperience: Int?,
    val frontDefaultSprite: String?,
    val frontShinySprite: String?,
    val backDefaultSprite: String?,
    val backShinySprite: String?
)
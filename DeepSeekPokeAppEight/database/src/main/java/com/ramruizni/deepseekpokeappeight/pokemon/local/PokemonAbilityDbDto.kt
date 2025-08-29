package com.ramruizni.deepseekpokeappeight.pokemon.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_abilities")
data class PokemonAbilityDbDto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pokemonId: Int,
    val slot: Int,
    val abilityName: String,
    val isHidden: Boolean
)
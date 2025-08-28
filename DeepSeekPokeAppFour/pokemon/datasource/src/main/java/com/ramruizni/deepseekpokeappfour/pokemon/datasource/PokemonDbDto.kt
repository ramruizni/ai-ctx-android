package com.ramruizni.deepseekpokeappfour.pokemon.datasource

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonDbDto(
    @PrimaryKey 
    val id: Int,
    val name: String,
    val number: Int,
    val imageUrl: String,
    val types: String, // JSON serialized list
    val abilities: String, // JSON serialized list
    val statsHp: Int,
    val statsAttack: Int,
    val statsDefense: Int,
    val statsSpecialAttack: Int,
    val statsSpecialDefense: Int,
    val statsSpeed: Int
)
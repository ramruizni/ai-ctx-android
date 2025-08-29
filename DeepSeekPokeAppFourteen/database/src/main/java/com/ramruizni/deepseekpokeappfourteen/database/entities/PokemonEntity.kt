package com.ramruizni.deepseekpokeappfourteen.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
    val typesJson: String,
    val abilitiesJson: String,
    val statsJson: String,
    val height: Int,
    val weight: Int
)
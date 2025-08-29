package com.example.deepseekpokeappeleven.pokemon.datasource.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.deepseekpokeappeleven.pokemon.datasource.converters.PokemonTypeConverters
import com.example.deepseekpokeappeleven.pokemon.datasource.dbdtos.PokemonAbilityDbDto
import com.example.deepseekpokeappeleven.pokemon.datasource.dbdtos.PokemonStatDbDto
import com.example.deepseekpokeappeleven.pokemon.datasource.dbdtos.PokemonTypeDbDto

@Entity(tableName = "pokemon")
@TypeConverters(PokemonTypeConverters::class)
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val number: Int,
    val imageUrl: String,
    val types: List<PokemonTypeDbDto>,
    val height: Int,
    val weight: Int,
    val abilities: List<PokemonAbilityDbDto>,
    val stats: List<PokemonStatDbDto>
)
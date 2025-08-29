package com.example.deepseekpokeappeleven.pokemon.datasource.converters

import androidx.room.TypeConverter
import com.example.deepseekpokeappeleven.pokemon.datasource.dbdtos.PokemonAbilityDbDto
import com.example.deepseekpokeappeleven.pokemon.datasource.dbdtos.PokemonStatDbDto
import com.example.deepseekpokeappeleven.pokemon.datasource.dbdtos.PokemonTypeDbDto
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PokemonTypeConverters {

    @TypeConverter
    fun fromTypeList(types: List<PokemonTypeDbDto>): String {
        return Json.encodeToString(types)
    }

    @TypeConverter
    fun toTypeList(typesJson: String): List<PokemonTypeDbDto> {
        return Json.decodeFromString(typesJson)
    }

    @TypeConverter
    fun fromAbilityList(abilities: List<PokemonAbilityDbDto>): String {
        return Json.encodeToString(abilities)
    }

    @TypeConverter
    fun toAbilityList(abilitiesJson: String): List<PokemonAbilityDbDto> {
        return Json.decodeFromString(abilitiesJson)
    }

    @TypeConverter
    fun fromStatList(stats: List<PokemonStatDbDto>): String {
        return Json.encodeToString(stats)
    }

    @TypeConverter
    fun toStatList(statsJson: String): List<PokemonStatDbDto> {
        return Json.decodeFromString(statsJson)
    }
}
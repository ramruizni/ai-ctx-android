package com.ramruizni.deepseekpokeappsix.database.converters.pokemon

import androidx.room.TypeConverter

class PokemonConverters {
    
    @TypeConverter
    fun fromString(value: String): List<String> {
        return if (value.isEmpty()) emptyList() else value.split(",")
    }

    @TypeConverter
    fun fromListString(list: List<String>): String {
        return list.joinToString(",")
    }
    
    @TypeConverter
    fun fromStatsString(value: String): Map<String, Int> {
        return if (value.isEmpty()) {
            emptyMap()
        } else {
            value.split(",").associate { 
                val parts = it.split(":")
                parts[0] to parts[1].toInt()
            }
        }
    }

    @TypeConverter
    fun fromStatsMap(map: Map<String, Int>): String {
        return map.entries.joinToString(",") { "${it.key}:${it.value}" }
    }
}
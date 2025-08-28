package com.ramruizni.deepseekpokeappfive.pokemon.datasource.converters

import androidx.room.TypeConverter
import com.ramruizni.deepseekpokeappfive.pokemon.domain.Pokemon
import com.ramruizni.deepseekpokeappfive.pokemon.domain.PokemonType
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class PokemonConverters {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    
    @TypeConverter
    fun fromTypesList(types: List<PokemonType>): String {
        return types.joinToString(",") { it.name }
    }
    
    @TypeConverter
    fun toTypesList(typesString: String): List<PokemonType> {
        return if (typesString.isBlank()) emptyList()
        else typesString.split(",").map { PokemonType.valueOf(it) }
    }
    
    @TypeConverter
    fun fromAbilitiesList(abilities: List<Pokemon.Ability>): String {
        val type = Types.newParameterizedType(List::class.java, Pokemon.Ability::class.java)
        val adapter = moshi.adapter<List<Pokemon.Ability>>(type)
        return adapter.toJson(abilities)
    }
    
    @TypeConverter
    fun toAbilitiesList(abilitiesString: String): List<Pokemon.Ability> {
        if (abilitiesString.isBlank()) return emptyList()
        val type = Types.newParameterizedType(List::class.java, Pokemon.Ability::class.java)
        val adapter = moshi.adapter<List<Pokemon.Ability>>(type)
        return adapter.fromJson(abilitiesString) ?: emptyList()
    }
    
    @TypeConverter
    fun fromStatsList(stats: List<Pokemon.Stat>): String {
        val type = Types.newParameterizedType(List::class.java, Pokemon.Stat::class.java)
        val adapter = moshi.adapter<List<Pokemon.Stat>>(type)
        return adapter.toJson(stats)
    }
    
    @TypeConverter
    fun toStatsList(statsString: String): List<Pokemon.Stat> {
        if (statsString.isBlank()) return emptyList()
        val type = Types.newParameterizedType(List::class.java, Pokemon.Stat::class.java)
        val adapter = moshi.adapter<List<Pokemon.Stat>>(type)
        return adapter.fromJson(statsString) ?: emptyList()
    }
}
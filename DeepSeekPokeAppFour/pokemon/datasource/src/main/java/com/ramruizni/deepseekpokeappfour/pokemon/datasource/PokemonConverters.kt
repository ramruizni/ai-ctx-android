package com.ramruizni.deepseekpokeappfour.pokemon.datasource

import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * JSON serialization helpers for Pokemon data
 */
object PokemonJsonConverter {
    
    fun List<String>.toJsonString(): String {
        return Json.encodeToString(this)
    }
    
    fun String.fromJsonStringToList(): List<String> {
        return Json.decodeFromString<List<String>>(this)
    }
}
package com.ramruizni.deepseekpokeappsix.pokemon.datasource.local

import com.ramruizni.deepseekpokeappsix.pokemon.domain.models.Pokemon
import com.ramruizni.deepseekpokeappsix.database.entities.pokemon.PokemonDbDto

fun Pokemon.toDbDto(): PokemonDbDto = PokemonDbDto(
    id = id,
    name = name,
    imageUrl = imageUrl,
    types = types.joinToString(","),
    height = height,
    weight = weight,
    abilities = abilities.joinToString(","),
    stats = stats.entries.joinToString(",") { "${it.key}:${it.value}" }
)

fun PokemonDbDto.toDomain(): Pokemon = Pokemon(
    id = id,
    name = name,
    imageUrl = imageUrl,
    types = if (types.isEmpty()) emptyList() else types.split(","),
    height = height,
    weight = weight,
    abilities = if (abilities.isEmpty()) emptyList() else abilities.split(","),
    stats = if (stats.isEmpty()) {
        emptyMap()
    } else {
        stats.split(",").associate { 
            val parts = it.split(":")
            parts[0] to parts[1].toInt()
        }
    }
)
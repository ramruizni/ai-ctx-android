package com.example.deepseekpokeapptwelve.pokemon.datasource.converters

import com.example.deepseekpokeapptwelve.pokemon.datasource.dbdtos.PokemonDbDto
import com.example.deepseekpokeapptwelve.pokemon.domain.models.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
}

fun Pokemon.convertToDbDto() = PokemonDbDto(
    id = id,
    name = name,
    height = height,
    weight = weight,
    baseExperience = baseExperience,
    typesJson = json.encodeToString(types),
    abilitiesJson = json.encodeToString(abilities),
    statsJson = json.encodeToString(stats),
    spritesJson = json.encodeToString(sprites)
)

fun PokemonDbDto.convertToDomain() = Pokemon(
    id = id,
    name = name,
    height = height,
    weight = weight,
    baseExperience = baseExperience,
    types = json.decodeFromString<List<PokemonType>>(typesJson),
    abilities = json.decodeFromString<List<PokemonAbility>>(abilitiesJson),
    stats = json.decodeFromString<List<PokemonStat>>(statsJson),
    sprites = json.decodeFromString<PokemonSprites>(spritesJson)
)
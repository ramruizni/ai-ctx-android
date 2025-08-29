package com.example.deepseekpokeapptwelve.pokemon.datasource.converters

import com.example.deepseekpokeapptwelve.pokemon.datasource.network.*
import com.example.deepseekpokeapptwelve.pokemon.domain.models.*

fun PokemonNetworkDto.toDomain() = Pokemon(
    id = id,
    name = name,
    height = height,
    weight = weight,
    baseExperience = baseExperience,
    types = types.map { it.toDomain() },
    abilities = abilities.map { it.toDomain() },
    stats = stats.map { it.toDomain() },
    sprites = sprites.toDomain()
)

fun PokemonTypeDto.toDomain() = PokemonType(
    slot = slot,
    name = type.name,
    url = type.url
)

fun PokemonAbilityDto.toDomain() = PokemonAbility(
    slot = slot,
    name = ability.name,
    url = ability.url,
    isHidden = isHidden
)

fun PokemonStatDto.toDomain() = PokemonStat(
    name = stat.name,
    url = stat.url,
    baseStat = baseStat,
    effort = effort
)

fun PokemonSpritesDto.toDomain() = PokemonSprites(
    frontDefault = frontDefault,
    frontShiny = frontShiny,
    backDefault = backDefault,
    backShiny = backShiny
)

fun PokemonListItemDto.toDomain() = PokemonListItem(
    name = name,
    url = url
)
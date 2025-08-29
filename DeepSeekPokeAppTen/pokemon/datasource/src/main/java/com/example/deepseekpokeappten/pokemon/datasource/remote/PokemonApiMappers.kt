package com.example.deepseekpokeappten.pokemon.datasource.remote

import com.example.deepseekpokeappten.pokemon.domain.NamedApiResource
import com.example.deepseekpokeappten.pokemon.domain.Pokemon
import com.example.deepseekpokeappten.pokemon.domain.PokemonAbility
import com.example.deepseekpokeappten.pokemon.domain.PokemonListItem
import com.example.deepseekpokeappten.pokemon.domain.PokemonListResponse
import com.example.deepseekpokeappten.pokemon.domain.PokemonSprites
import com.example.deepseekpokeappten.pokemon.domain.PokemonStat
import com.example.deepseekpokeappten.pokemon.domain.PokemonType

fun PokemonListResponseDto.toDomain(): PokemonListResponse = PokemonListResponse(
    count = count,
    next = next,
    previous = previous,
    results = results.map { it.toDomain() }
)

fun PokemonListItemDto.toDomain(): PokemonListItem = PokemonListItem(
    name = name,
    url = url
)

fun PokemonDto.toDomain(): Pokemon = Pokemon(
    id = id,
    name = name,
    height = height,
    weight = weight,
    types = types.map { it.toDomain() },
    sprites = sprites.toDomain(),
    abilities = abilities.map { it.toDomain() },
    stats = stats.map { it.toDomain() }
)

fun PokemonTypeDto.toDomain(): PokemonType = PokemonType(
    slot = slot,
    type = type.toDomain()
)

fun NamedApiResourceDto.toDomain(): NamedApiResource = NamedApiResource(
    name = name,
    url = url
)

fun PokemonSpritesDto.toDomain(): PokemonSprites = PokemonSprites(
    frontDefault = frontDefault,
    frontShiny = frontShiny,
    frontFemale = frontFemale,
    frontShinyFemale = frontShinyFemale,
    backDefault = backDefault,
    backShiny = backShiny,
    backFemale = backFemale,
    backShinyFemale = backShinyFemale
)

fun PokemonAbilityDto.toDomain(): PokemonAbility = PokemonAbility(
    isHidden = isHidden,
    slot = slot,
    ability = ability.toDomain()
)

fun PokemonStatDto.toDomain(): PokemonStat = PokemonStat(
    baseStat = baseStat,
    effort = effort,
    stat = stat.toDomain()
)
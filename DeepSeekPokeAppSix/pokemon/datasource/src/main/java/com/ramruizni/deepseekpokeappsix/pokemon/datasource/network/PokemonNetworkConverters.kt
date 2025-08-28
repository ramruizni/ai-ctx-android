package com.ramruizni.deepseekpokeappsix.pokemon.datasource.network

import com.ramruizni.deepseekpokeappsix.database.entities.pokemon.PokemonDbDto
import com.ramruizni.deepseekpokeappsix.pokemon.domain.models.Pokemon

fun PokemonNetworkDto.toDbDto(): PokemonDbDto {
    return PokemonDbDto(
        id = id,
        name = name,
        imageUrl = sprites.frontDefault.orEmpty(),
        types = types.joinToString(",") { it.type.name },
        height = height,
        weight = weight,
        abilities = abilities.joinToString(",") { it.ability.name },
        stats = stats.associate { 
            it.stat.name to it.baseStat 
        }.entries.joinToString(",") { "${it.key}:${it.value}" }
    )
}

fun PokemonNetworkDto.toDomain(): Pokemon {
    return Pokemon(
        id = id,
        name = name.replaceFirstChar { it.uppercase() },
        imageUrl = sprites.frontDefault.orEmpty(),
        types = types.map { it.type.name.replaceFirstChar { it.uppercase() } },
        height = height,
        weight = weight,
        abilities = abilities.map { it.ability.name.replaceFirstChar { it.uppercase() } },
        stats = stats.associate { 
            it.stat.name.replaceFirstChar { it.uppercase() } to it.baseStat 
        }
    )
}

fun PokemonListItemDto.extractId(): Int {
    return url.removeSuffix("/").split("/").last().toInt()
}
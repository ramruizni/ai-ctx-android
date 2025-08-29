package com.ramruizni.deepseekpokeappfourteen.pokemon.infrastructure.mappers

import com.ramruizni.deepseekpokeappfourteen.database.entities.PokemonEntity
import com.ramruizni.deepseekpokeappfourteen.pokemon.datasource.remote.*
import com.ramruizni.deepseekpokeappfourteen.pokemon.domain.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun PokemonDetailsResponse.toDomain(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        imageUrl = sprites.frontDefault ?: "",
        types = types.map { typeResponse ->
            PokemonType(
                name = typeResponse.type.name,
                url = typeResponse.type.url
            )
        },
        abilities = abilities.map { abilityResponse ->
            PokemonAbility(
                name = abilityResponse.ability.name,
                url = abilityResponse.ability.url,
                isHidden = abilityResponse.isHidden
            )
        },
        stats = stats.map { statResponse ->
            PokemonStat(
                name = statResponse.stat.name,
                baseStat = statResponse.baseStat,
                effort = statResponse.effort,
                url = statResponse.stat.url
            )
        },
        height = height,
        weight = weight
    )
}

fun Pokemon.toEntity(): PokemonEntity {
    return PokemonEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
        typesJson = Json.encodeToString(types.map { "${it.name}|${it.url}" }),
        abilitiesJson = Json.encodeToString(abilities.map { "${it.name}|${it.url}|${it.isHidden}" }),
        statsJson = Json.encodeToString(stats.map { "${it.name}|${it.baseStat}|${it.effort}|${it.url}" }),
        height = height,
        weight = weight
    )
}

fun PokemonEntity.toDomain(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        imageUrl = imageUrl,
        types = try {
            Json.decodeFromString<List<String>>(typesJson).map { typeString ->
                val parts = typeString.split("|")
                PokemonType(
                    name = parts[0],
                    url = parts.getOrElse(1) { "" }
                )
            }
        } catch (e: Exception) {
            emptyList()
        },
        abilities = try {
            Json.decodeFromString<List<String>>(abilitiesJson).map { abilityString ->
                val parts = abilityString.split("|")
                PokemonAbility(
                    name = parts[0],
                    url = parts.getOrElse(1) { "" },
                    isHidden = parts.getOrElse(2) { "false" }.toBoolean()
                )
            }
        } catch (e: Exception) {
            emptyList()
        },
        stats = try {
            Json.decodeFromString<List<String>>(statsJson).map { statString ->
                val parts = statString.split("|")
                PokemonStat(
                    name = parts[0],
                    baseStat = parts.getOrElse(1) { "0" }.toInt(),
                    effort = parts.getOrElse(2) { "0" }.toInt(),
                    url = parts.getOrElse(3) { "" }
                )
            }
        } catch (e: Exception) {
            emptyList()
        },
        height = height,
        weight = weight
    )
}
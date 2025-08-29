package com.example.deepseekpokeappten.pokemon.datasource.mappers

import com.example.deepseekpokeappten.database.entities.PokemonEntity
import com.example.deepseekpokeappten.pokemon.domain.NamedApiResource
import com.example.deepseekpokeappten.pokemon.domain.Pokemon
import com.example.deepseekpokeappten.pokemon.domain.PokemonAbility
import com.example.deepseekpokeappten.pokemon.domain.PokemonSprites
import com.example.deepseekpokeappten.pokemon.domain.PokemonStat
import com.example.deepseekpokeappten.pokemon.domain.PokemonType
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val json = Json { ignoreUnknownKeys = true }

@Serializable
data class PokemonTypeEntity(
    val slot: Int,
    val typeName: String,
    val typeUrl: String
)

@Serializable
data class PokemonSpritesEntity(
    val frontDefault: String? = null,
    val frontShiny: String? = null,
    val frontFemale: String? = null,
    val frontShinyFemale: String? = null,
    val backDefault: String? = null,
    val backShiny: String? = null,
    val backFemale: String? = null,
    val backShinyFemale: String? = null
)

@Serializable
data class PokemonAbilityEntity(
    val isHidden: Boolean,
    val slot: Int,
    val abilityName: String,
    val abilityUrl: String
)

@Serializable
data class PokemonStatEntity(
    val baseStat: Int,
    val effort: Int,
    val statName: String,
    val statUrl: String
)

fun Pokemon.toEntity(): PokemonEntity = PokemonEntity(
    id = id,
    name = name,
    height = height,
    weight = weight,
    types = json.encodeToString(types.map { it.toEntity() }),
    sprites = json.encodeToString(sprites.toEntity()),
    abilities = json.encodeToString(abilities.map { it.toEntity() }),
    stats = json.encodeToString(stats.map { it.toEntity() })
)

fun PokemonEntity.toDomain(): Pokemon = Pokemon(
    id = id,
    name = name,
    height = height,
    weight = weight,
    types = json.decodeFromString<List<PokemonTypeEntity>>(types).map { it.toDomain() },
    sprites = json.decodeFromString<PokemonSpritesEntity>(sprites).toDomain(),
    abilities = json.decodeFromString<List<PokemonAbilityEntity>>(abilities).map { it.toDomain() },
    stats = json.decodeFromString<List<PokemonStatEntity>>(stats).map { it.toDomain() }
)

fun PokemonType.toEntity(): PokemonTypeEntity = PokemonTypeEntity(
    slot = slot,
    typeName = type.name,
    typeUrl = type.url
)

fun PokemonTypeEntity.toDomain(): PokemonType = PokemonType(
    slot = slot,
    type = NamedApiResource(name = typeName, url = typeUrl)
)

fun PokemonSprites.toEntity(): PokemonSpritesEntity = PokemonSpritesEntity(
    frontDefault = frontDefault,
    frontShiny = frontShiny,
    frontFemale = frontFemale,
    frontShinyFemale = frontShinyFemale,
    backDefault = backDefault,
    backShiny = backShiny,
    backFemale = backFemale,
    backShinyFemale = backShinyFemale
)

fun PokemonSpritesEntity.toDomain(): PokemonSprites = PokemonSprites(
    frontDefault = frontDefault,
    frontShiny = frontShiny,
    frontFemale = frontFemale,
    frontShinyFemale = frontShinyFemale,
    backDefault = backDefault,
    backShiny = backShiny,
    backFemale = backFemale,
    backShinyFemale = backShinyFemale
)

fun PokemonAbility.toEntity(): PokemonAbilityEntity = PokemonAbilityEntity(
    isHidden = isHidden,
    slot = slot,
    abilityName = ability.name,
    abilityUrl = ability.url
)

fun PokemonAbilityEntity.toDomain(): PokemonAbility = PokemonAbility(
    isHidden = isHidden,
    slot = slot,
    ability = NamedApiResource(name = abilityName, url = abilityUrl)
)

fun PokemonStat.toEntity(): PokemonStatEntity = PokemonStatEntity(
    baseStat = baseStat,
    effort = effort,
    statName = stat.name,
    statUrl = stat.url
)

fun PokemonStatEntity.toDomain(): PokemonStat = PokemonStat(
    baseStat = baseStat,
    effort = effort,
    stat = NamedApiResource(name = statName, url = statUrl)
)
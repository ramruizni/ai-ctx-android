package com.ramruizni.deepseekpokeappthirteen.pokemon.infrastructure.mappers

import com.ramruizni.deepseekpokeappthirteen.pokemon.datasource.dbdtos.PokemonDbDto
import com.ramruizni.deepseekpokeappthirteen.pokemon.datasource.remote.*
import com.ramruizni.deepseekpokeappthirteen.pokemon.domain.model.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun PokemonDbDto.toDomain(): Pokemon {
    val types = Json.decodeFromString<List<PokemonType>>(this.types)
    val abilities = Json.decodeFromString<List<PokemonAbility>>(this.abilities)
    val stats = Json.decodeFromString<List<PokemonStat>>(this.stats)
    
    return Pokemon(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        height = this.height,
        weight = this.weight,
        baseExperience = this.baseExperience,
        types = types,
        abilities = abilities,
        stats = stats
    )
}

fun PokemonDetailResponse.toDbDto(): PokemonDbDto {
    val types = this.types.map { typeResponse ->
        PokemonType(
            name = typeResponse.type.name,
            slot = typeResponse.slot
        )
    }
    
    val abilities = this.abilities.map { abilityResponse ->
        PokemonAbility(
            name = abilityResponse.ability.name,
            isHidden = abilityResponse.isHidden,
            slot = abilityResponse.slot
        )
    }
    
    val stats = this.stats.map { statResponse ->
        PokemonStat(
            name = statResponse.stat.name,
            baseStat = statResponse.baseStat,
            effort = statResponse.effort
        )
    }
    
    return PokemonDbDto(
        id = this.id,
        name = this.name,
        imageUrl = this.sprites.frontDefault ?: "",
        height = this.height,
        weight = this.weight,
        baseExperience = this.baseExperience ?: 0,
        types = Json.encodeToString(types),
        abilities = Json.encodeToString(abilities),
        stats = Json.encodeToString(stats)
    )
}
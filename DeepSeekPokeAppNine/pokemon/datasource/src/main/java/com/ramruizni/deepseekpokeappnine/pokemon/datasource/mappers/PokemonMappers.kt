package com.ramruizni.deepseekpokeappnine.pokemon.datasource.mappers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ramruizni.deepseekpokeappnine.pokemon.datasource.dbdtos.PokemonDbDto
import com.ramruizni.deepseekpokeappnine.pokemon.datasource.dbdtos.PokemonStatDbDto
import com.ramruizni.deepseekpokeappnine.pokemon.datasource.remote.PokemonDetailResponse
import com.ramruizni.deepseekpokeappnine.pokemon.domain.Pokemon
import com.ramruizni.deepseekpokeappnine.pokemon.domain.PokemonStat

private val gson = Gson()

fun PokemonDetailResponse.toDbDto(): PokemonDbDto {
    val types = this.types.map { it.type.name }
    val abilities = this.abilities.map { it.ability.name }
    val stats = this.stats.map { 
        PokemonStatDbDto(
            name = it.stat.name,
            baseStat = it.baseStat,
            effort = it.effort
        )
    }
    
    return PokemonDbDto(
        id = this.id,
        name = this.name,
        imageUrl = this.sprites.other?.officialArtwork?.frontDefault 
            ?: this.sprites.frontDefault 
            ?: "",
        types = gson.toJson(types),
        abilities = gson.toJson(abilities),
        stats = gson.toJson(stats),
        height = this.height,
        weight = this.weight
    )
}

fun PokemonDbDto.toDomain(): Pokemon {
    val typesListType = object : TypeToken<List<String>>() {}.type
    val abilitiesListType = object : TypeToken<List<String>>() {}.type
    val statsListType = object : TypeToken<List<PokemonStatDbDto>>() {}.type
    
    val types: List<String> = try {
        gson.fromJson(this.types, typesListType)
    } catch (e: Exception) {
        emptyList()
    }
    
    val abilities: List<String> = try {
        gson.fromJson(this.abilities, abilitiesListType)
    } catch (e: Exception) {
        emptyList()
    }
    
    val statsDto: List<PokemonStatDbDto> = try {
        gson.fromJson(this.stats, statsListType)
    } catch (e: Exception) {
        emptyList()
    }
    
    return Pokemon(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        types = types,
        abilities = abilities,
        stats = statsDto.map { 
            PokemonStat(
                name = it.name,
                baseStat = it.baseStat,
                effort = it.effort
            )
        },
        height = this.height,
        weight = this.weight
    )
}
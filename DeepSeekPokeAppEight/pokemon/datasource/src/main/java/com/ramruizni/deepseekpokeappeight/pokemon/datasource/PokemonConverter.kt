package com.ramruizni.deepseekpokeappeight.pokemon.datasource

import com.ramruizni.deepseekpokeappeight.pokemon.datasource.remote.PokemonDetailResponse
import com.ramruizni.deepseekpokeappeight.pokemon.local.PokemonAbilityDbDto
import com.ramruizni.deepseekpokeappeight.pokemon.local.PokemonDbDto
import com.ramruizni.deepseekpokeappeight.pokemon.local.PokemonStatDbDto
import com.ramruizni.deepseekpokeappeight.pokemon.local.PokemonTypeDbDto

object PokemonConverter {
    
    fun fromApiResponseToDbDto(response: PokemonDetailResponse): PokemonDbDto {
        return PokemonDbDto(
            id = response.id,
            name = response.name,
            height = response.height,
            weight = response.weight,
            baseExperience = response.base_experience,
            frontDefaultSprite = response.sprites.front_default,
            frontShinySprite = response.sprites.front_shiny,
            backDefaultSprite = response.sprites.back_default,
            backShinySprite = response.sprites.back_shiny
        )
    }
    
    fun fromApiResponseToTypeDbDtos(pokemonId: Int, response: PokemonDetailResponse): List<PokemonTypeDbDto> {
        return response.types.map { typeApi ->
            PokemonTypeDbDto(
                pokemonId = pokemonId,
                slot = typeApi.slot,
                typeName = typeApi.type.name
            )
        }
    }
    
    fun fromApiResponseToAbilityDbDtos(pokemonId: Int, response: PokemonDetailResponse): List<PokemonAbilityDbDto> {
        return response.abilities.map { abilityApi ->
            PokemonAbilityDbDto(
                pokemonId = pokemonId,
                slot = abilityApi.slot,
                abilityName = abilityApi.ability.name,
                isHidden = abilityApi.is_hidden
            )
        }
    }
    
    fun fromApiResponseToStatDbDtos(pokemonId: Int, response: PokemonDetailResponse): List<PokemonStatDbDto> {
        return response.stats.map { statApi ->
            PokemonStatDbDto(
                pokemonId = pokemonId,
                statName = statApi.stat.name,
                baseStat = statApi.base_stat,
                effort = statApi.effort
            )
        }
    }
}
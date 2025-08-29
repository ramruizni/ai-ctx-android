package com.ramruizni.deepseekpokeappeight.pokemon.infrastructure

import com.ramruizni.deepseekpokeappeight.pokemon.local.PokemonWithDetails
import com.ramruizni.deepseekpokeappeight.pokemon.domain.Pokemon
import com.ramruizni.deepseekpokeappeight.pokemon.domain.PokemonType
import com.ramruizni.deepseekpokeappeight.pokemon.domain.PokemonAbility
import com.ramruizni.deepseekpokeappeight.pokemon.domain.PokemonStat

/**
 * Maps data layer models to domain models
 * Uses proper domain models from the pokemon:domain module
 */
object PokemonDomainMapper {
    
    fun toDomain(pokemonWithDetails: PokemonWithDetails): Pokemon {
        return Pokemon(
            id = pokemonWithDetails.pokemon.id,
            name = pokemonWithDetails.pokemon.name,
            imageUrl = pokemonWithDetails.pokemon.frontDefaultSprite ?: "",
            types = pokemonWithDetails.types.map { typeDbDto ->
                PokemonType(
                    name = typeDbDto.typeName
                )
            },
            abilities = pokemonWithDetails.abilities.map { abilityDbDto ->
                PokemonAbility(
                    name = abilityDbDto.abilityName,
                    isHidden = abilityDbDto.isHidden
                )
            },
            stats = pokemonWithDetails.stats.map { statDbDto ->
                PokemonStat(
                    name = statDbDto.statName,
                    baseStat = statDbDto.baseStat,
                    effort = statDbDto.effort
                )
            }
        )
    }
}
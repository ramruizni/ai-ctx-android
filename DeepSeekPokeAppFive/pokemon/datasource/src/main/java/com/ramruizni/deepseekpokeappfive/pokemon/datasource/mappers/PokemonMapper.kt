package com.ramruizni.deepseekpokeappfive.pokemon.datasource.mappers

import com.ramruizni.deepseekpokeappfive.pokemon.datasource.dbdtos.PokemonDbDto
import com.ramruizni.deepseekpokeappfive.pokemon.domain.Pokemon
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class PokemonMapper {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    
    fun toDbDto(pokemon: Pokemon): PokemonDbDto {
        val abilitiesType = Types.newParameterizedType(List::class.java, Pokemon.Ability::class.java)
        val statsType = Types.newParameterizedType(List::class.java, Pokemon.Stat::class.java)
        
        val abilitiesAdapter = moshi.adapter<List<Pokemon.Ability>>(abilitiesType)
        val statsAdapter = moshi.adapter<List<Pokemon.Stat>>(statsType)
        
        return PokemonDbDto(
            id = pokemon.id,
            name = pokemon.name,
            number = pokemon.number,
            imageUrl = pokemon.imageUrl,
            types = pokemon.types.joinToString(",") { it.name },
            abilities = abilitiesAdapter.toJson(pokemon.abilities),
            stats = statsAdapter.toJson(pokemon.stats)
        )
    }
    
    fun toDomain(pokemonDbDto: PokemonDbDto): Pokemon {
        val abilitiesType = Types.newParameterizedType(List::class.java, Pokemon.Ability::class.java)
        val statsType = Types.newParameterizedType(List::class.java, Pokemon.Stat::class.java)
        
        val abilitiesAdapter = moshi.adapter<List<Pokemon.Ability>>(abilitiesType)
        val statsAdapter = moshi.adapter<List<Pokemon.Stat>>(statsType)
        
        return Pokemon(
            id = pokemonDbDto.id,
            name = pokemonDbDto.name,
            number = pokemonDbDto.number,
            imageUrl = pokemonDbDto.imageUrl,
            types = pokemonDbDto.types.split(",").map { PokemonType.valueOf(it) },
            abilities = abilitiesAdapter.fromJson(pokemonDbDto.abilities) ?: emptyList(),
            stats = statsAdapter.fromJson(pokemonDbDto.stats) ?: emptyList()
        )
    }
}
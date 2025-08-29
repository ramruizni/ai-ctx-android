package com.ramruizni.deepseekpokeappeight.pokemon.local

import androidx.room.Embedded
import androidx.room.Relation

data class PokemonWithDetails(
    @Embedded val pokemon: PokemonDbDto,
    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId"
    )
    val types: List<PokemonTypeDbDto>,
    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId"
    )
    val abilities: List<PokemonAbilityDbDto>,
    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId"
    )
    val stats: List<PokemonStatDbDto>
)
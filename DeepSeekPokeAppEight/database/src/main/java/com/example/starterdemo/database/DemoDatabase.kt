package com.ramruizni.deepseekpokeappeight.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ramruizni.deepseekpokeappeight.pokemon.local.PokemonAbilityDbDto
import com.ramruizni.deepseekpokeappeight.pokemon.local.PokemonDao
import com.ramruizni.deepseekpokeappeight.pokemon.local.PokemonDbDto
import com.ramruizni.deepseekpokeappeight.pokemon.local.PokemonStatDbDto
import com.ramruizni.deepseekpokeappeight.pokemon.local.PokemonTypeDbDto

@Database(
    entities = [
        PokemonDbDto::class,
        PokemonTypeDbDto::class,
        PokemonAbilityDbDto::class,
        PokemonStatDbDto::class
    ],
    version = 1,
    exportSchema = true
)
abstract class DeepSeekPokeAppEightDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
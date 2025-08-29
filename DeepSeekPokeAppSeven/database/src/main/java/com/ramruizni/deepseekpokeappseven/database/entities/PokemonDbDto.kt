package com.ramruizni.deepseekpokeappseven.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ramruizni.deepseekpokeappseven.pokemon.domain.Pokemon
import com.ramruizni.deepseekpokeappseven.pokemon.domain.PokemonAbility
import com.ramruizni.deepseekpokeappseven.pokemon.domain.PokemonStat
import com.ramruizni.deepseekpokeappseven.pokemon.domain.PokemonType

@Entity(tableName = "pokemon")
data class PokemonDbDto(
    @PrimaryKey 
    val id: Int,
    val name: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    val types: String, // JSON serialized list
    val abilities: String, // JSON serialized list
    val stats: String, // JSON serialized list
    val height: Int,
    val weight: Int
) {
    fun toDomain(): Pokemon {
        return Pokemon(
            id = id,
            name = name,
            imageUrl = imageUrl,
            types = parseTypes(types),
            abilities = parseAbilities(abilities),
            stats = parseStats(stats),
            height = height,
            weight = weight
        )
    }

    companion object {
        fun fromDomain(pokemon: Pokemon): PokemonDbDto {
            return PokemonDbDto(
                id = pokemon.id,
                name = pokemon.name,
                imageUrl = pokemon.imageUrl,
                types = serializeTypes(pokemon.types),
                abilities = serializeAbilities(pokemon.abilities),
                stats = serializeStats(pokemon.stats),
                height = pokemon.height,
                weight = pokemon.weight
            )
        }

        private fun parseTypes(json: String): List<PokemonType> {
            if (json.isBlank()) return emptyList()
            return json.split("|").map { typeString ->
                val parts = typeString.split(",")
                PokemonType(
                    name = parts[0],
                    color = if (parts.size > 1) parts[1] else getTypeColor(parts[0])
                )
            }
        }

        private fun parseAbilities(json: String): List<PokemonAbility> {
            if (json.isBlank()) return emptyList()
            return json.split("|").map { abilityString ->
                val parts = abilityString.split(",")
                PokemonAbility(
                    name = parts[0],
                    isHidden = parts.getOrNull(1)?.toBoolean() ?: false
                )
            }
        }

        private fun parseStats(json: String): List<PokemonStat> {
            if (json.isBlank()) return emptyList()
            return json.split("|").map { statString ->
                val parts = statString.split(",")
                PokemonStat(
                    name = parts[0],
                    baseStat = parts.getOrNull(1)?.toInt() ?: 0,
                    effort = parts.getOrNull(2)?.toInt() ?: 0
                )
            }
        }

        private fun serializeTypes(types: List<PokemonType>): String {
            return types.joinToString("|") { "${it.name},${it.color}" }
        }

        private fun serializeAbilities(abilities: List<PokemonAbility>): String {
            return abilities.joinToString("|") { "${it.name},${it.isHidden}" }
        }

        private fun serializeStats(stats: List<PokemonStat>): String {
            return stats.joinToString("|") { "${it.name},${it.baseStat},${it.effort}" }
        }

        private fun getTypeColor(typeName: String): String {
            return when (typeName.lowercase()) {
                "normal" -> "#A8A878"
                "fire" -> "#F08030"
                "water" -> "#6890F0"
                "electric" -> "#F8D030"
                "grass" -> "#78C850"
                "ice" -> "#98D8D8"
                "fighting" -> "#C03028"
                "poison" -> "#A040A0"
                "ground" -> "#E0C068"
                "flying" -> "#A890F0"
                "psychic" -> "#F85888"
                "bug" -> "#A8B820"
                "rock" -> "#B8A038"
                "ghost" -> "#705898"
                "dragon" -> "#7038F8"
                "dark" -> "#705848"
                "steel" -> "#B8B8D0"
                "fairy" -> "#EE99AC"
                else -> "#68A090"
            }
        }
    }
}
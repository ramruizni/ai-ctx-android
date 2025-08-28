package com.ramruizni.deepseekpokeappfour.features.pokemon.viewmodel

/**
 * UI state for the Pokemon screen
 */
data class PokemonUiState(
    val isLoading: Boolean = false,
    val pokemon: List<Pokemon> = emptyList(),
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false
)

/**
 * Represents a Pokemon for UI display
 */
data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<PokemonType>
)

/**
 * Represents a Pokemon type with display properties
 */
data class PokemonType(
    val name: String,
    val color: Long
) {
    companion object {
        // Type colors for display
        val TYPE_COLORS = mapOf(
            "normal" to 0xFFA8A878,
            "fire" to 0xFFF08030,
            "water" to 0xFF6890F0,
            "electric" to 0xFFF8D030,
            "grass" to 0xFF78C850,
            "ice" to 0xFF98D8D8,
            "fighting" to 0xFFC03028,
            "poison" to 0xFFA040A0,
            "ground" to 0xFFE0C068,
            "flying" to 0xFFA890F0,
            "psychic" to 0xFFF85888,
            "bug" to 0xFFA8B820,
            "rock" to 0xFFB8A038,
            "ghost" to 0xFF705898,
            "dragon" to 0xFF7038F8,
            "dark" to 0xFF705848,
            "steel" to 0xFFB8B8D0,
            "fairy" to 0xFFEE99AC
        )
        
        fun getTypeColor(typeName: String): Long {
            return TYPE_COLORS[typeName.lowercase()] ?: 0xFFA8A878 // Default to normal type color
        }
    }
}
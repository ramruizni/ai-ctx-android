package com.ramruizni.deepseekpokeappeight.pokemon.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramruizni.deepseekpokeappeight.pokemon.domain.PokemonType

/**
 * Composable that displays a Pokemon type as a colored chip
 * Each type has its own distinctive color based on the Pokemon universe
 */
@Composable
fun PokemonTypeChip(
    type: PokemonType,
    modifier: Modifier = Modifier
) {
    Text(
        text = type.displayName,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(getTypeColor(type.name))
            .padding(horizontal = 12.dp, vertical = 4.dp),
        color = Color.White,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        style = MaterialTheme.typography.labelMedium
    )
}

/**
 * Returns the color associated with each Pokemon type
 * Colors are based on the official Pokemon type color scheme
 */
private fun getTypeColor(typeName: String): Color {
    return when (typeName.lowercase()) {
        "normal" -> Color(0xFFA8A878)
        "fire" -> Color(0xFFF08030)
        "water" -> Color(0xFF6890F0)
        "electric" -> Color(0xFFF8D030)
        "grass" -> Color(0xFF78C850)
        "ice" -> Color(0xFF98D8D8)
        "fighting" -> Color(0xFFC03028)
        "poison" -> Color(0xFFA040A0)
        "ground" -> Color(0xFFE0C068)
        "flying" -> Color(0xFFA890F0)
        "psychic" -> Color(0xFFF85888)
        "bug" -> Color(0xFFA8B820)
        "rock" -> Color(0xFFB8A038)
        "ghost" -> Color(0xFF705898)
        "dragon" -> Color(0xFF7038F8)
        "dark" -> Color(0xFF705848)
        "steel" -> Color(0xFFB8B8D0)
        "fairy" -> Color(0xFFEE99AC)
        else -> Color(0xFF68A090) // Default color for unknown types
    }
}

@Preview(showBackground = true)
@Composable
private fun PokemonTypeChipPreview() {
    MaterialTheme {
        PokemonTypeChip(
            type = PokemonType(name = "fire")
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PokemonTypeChipMultiplePreview() {
    MaterialTheme {
        androidx.compose.foundation.layout.Row(
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
        ) {
            PokemonTypeChip(type = PokemonType(name = "fire"))
            PokemonTypeChip(type = PokemonType(name = "flying"))
            PokemonTypeChip(type = PokemonType(name = "water"))
            PokemonTypeChip(type = PokemonType(name = "grass"))
        }
    }
}
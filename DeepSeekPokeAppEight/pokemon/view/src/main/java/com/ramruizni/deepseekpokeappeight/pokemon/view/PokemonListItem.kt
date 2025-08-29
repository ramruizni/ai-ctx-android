package com.ramruizni.deepseekpokeappeight.pokemon.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ramruizni.deepseekpokeappeight.pokemon.domain.Pokemon
import com.ramruizni.deepseekpokeappeight.pokemon.domain.PokemonType

/**
 * Individual Pokemon card item for the grid layout
 * Shows Pokemon image, name, number, and type chips
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PokemonListItem(
    pokemon: Pokemon,
    onPokemonClick: (Pokemon) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.85f) // Slightly taller than wide
            .clickable { onPokemonClick(pokemon) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            hoveredElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            // Pokemon Number
            Text(
                text = pokemon.formattedNumber,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // Pokemon Image
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(pokemon.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "${pokemon.displayName} sprite",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    onState = { state ->
                        when (state) {
                            is AsyncImagePainter.State.Loading -> {
                                // Show loading indicator
                            }
                            is AsyncImagePainter.State.Error -> {
                                // Show error placeholder
                            }
                            else -> {
                                // Success or empty
                            }
                        }
                    }
                )
                
                // Loading indicator overlay
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(pokemon.imageUrl)
                        .build()
                )
                
                if (painter.state is AsyncImagePainter.State.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Pokemon Name
            Text(
                text = pokemon.displayName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Pokemon Types
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                pokemon.types.forEach { type ->
                    PokemonTypeChip(
                        type = type,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

/**
 * Shimmer loading placeholder for Pokemon list items
 */
@Composable
fun PokemonListItemPlaceholder(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.85f),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            // Number placeholder
            Box(
                modifier = Modifier
                    .size(width = 40.dp, height = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray.copy(alpha = 0.3f))
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Image placeholder
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Gray.copy(alpha = 0.3f))
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Name placeholder
            Box(
                modifier = Modifier
                    .size(width = 80.dp, height = 20.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Gray.copy(alpha = 0.3f))
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Type chips placeholder
            Box(
                modifier = Modifier
                    .size(width = 60.dp, height = 24.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Gray.copy(alpha = 0.3f))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PokemonListItemPreview() {
    MaterialTheme {
        PokemonListItem(
            pokemon = Pokemon(
                id = 25,
                name = "pikachu",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png",
                types = listOf(
                    PokemonType(name = "electric")
                )
            ),
            onPokemonClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PokemonListItemMultiTypePreview() {
    MaterialTheme {
        PokemonListItem(
            pokemon = Pokemon(
                id = 6,
                name = "charizard",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png",
                types = listOf(
                    PokemonType(name = "fire"),
                    PokemonType(name = "flying")
                )
            ),
            onPokemonClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PokemonListItemPlaceholderPreview() {
    MaterialTheme {
        PokemonListItemPlaceholder()
    }
}
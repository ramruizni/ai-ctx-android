package com.ramruizni.deepseekpokeappfour.features.pokemon.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramruizni.deepseekpokeappfour.features.pokemon.view.components.PokemonItem
import com.ramruizni.deepseekpokeappfour.features.pokemon.viewmodel.Pokemon
import com.ramruizni.deepseekpokeappfour.features.pokemon.viewmodel.PokemonType
import com.ramruizni.deepseekpokeappfour.features.pokemon.viewmodel.PokemonUiState
import com.ramruizni.deepseekpokeappfour.features.pokemon.viewmodel.PokemonViewModel

@Composable
fun PokemonScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    PokemonScreenContent(
        uiState = uiState,
        onRefresh = { viewModel.refreshPokemon() },
        onRetry = { viewModel.loadPokemon() },
        onErrorDismiss = { viewModel.clearError() },
        onPokemonClick = { pokemon ->
            // TODO: Navigate to Pokemon detail screen when available
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PokemonScreenContent(
    uiState: PokemonUiState,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
    onErrorDismiss: () -> Unit,
    onPokemonClick: (Pokemon) -> Unit,
    modifier: Modifier = Modifier
) {
    PullToRefreshBox(
        isRefreshing = uiState.isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier.fillMaxSize()
    ) {
        when {
            uiState.isLoading && uiState.pokemon.isEmpty() -> {
                LoadingContent()
            }
            uiState.errorMessage != null && uiState.pokemon.isEmpty() -> {
                ErrorContent(
                    errorMessage = uiState.errorMessage ?: "Unknown error",
                    onRetry = onRetry,
                    onDismiss = onErrorDismiss
                )
            }
            uiState.pokemon.isEmpty() -> {
                EmptyContent()
            }
            else -> {
                PokemonGridContent(
                    pokemon = uiState.pokemon,
                    onPokemonClick = onPokemonClick
                )
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator()
            Text(
                text = "Loading Pokémon...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun ErrorContent(
    errorMessage: String,
    onRetry: () -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Oops! Something went wrong",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
                )
                
                Text(
                    text = errorMessage,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                
                Button(onClick = onRetry) {
                    Text("Try Again")
                }
            }
        }
    }
}

@Composable
private fun EmptyContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "No Pokémon Found",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            
            Text(
                text = "Pull down to refresh",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
private fun PokemonGridContent(
    pokemon: List<Pokemon>,
    onPokemonClick: (Pokemon) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(pokemon) { pokemonItem ->
            PokemonItem(
                pokemon = pokemonItem,
                onClick = { onPokemonClick(pokemonItem) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PokemonScreenLoadingPreview() {
    MaterialTheme {
        PokemonScreenContent(
            uiState = PokemonUiState(isLoading = true),
            onRefresh = {},
            onRetry = {},
            onErrorDismiss = {},
            onPokemonClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PokemonScreenContentPreview() {
    MaterialTheme {
        PokemonScreenContent(
            uiState = PokemonUiState(
                pokemon = listOf(
                    Pokemon(
                        id = 1,
                        name = "Bulbasaur",
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                        types = listOf(
                            PokemonType("grass", PokemonType.getTypeColor("grass")),
                            PokemonType("poison", PokemonType.getTypeColor("poison"))
                        )
                    ),
                    Pokemon(
                        id = 25,
                        name = "Pikachu",
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png",
                        types = listOf(
                            PokemonType("electric", PokemonType.getTypeColor("electric"))
                        )
                    )
                )
            ),
            onRefresh = {},
            onRetry = {},
            onErrorDismiss = {},
            onPokemonClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PokemonScreenErrorPreview() {
    MaterialTheme {
        PokemonScreenContent(
            uiState = PokemonUiState(errorMessage = "Network connection failed"),
            onRefresh = {},
            onRetry = {},
            onErrorDismiss = {},
            onPokemonClick = {}
        )
    }
}
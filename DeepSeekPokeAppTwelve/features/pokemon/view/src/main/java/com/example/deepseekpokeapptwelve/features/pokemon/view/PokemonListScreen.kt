package com.example.deepseekpokeapptwelve.features.pokemon.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.deepseekpokeapptwelve.features.pokemon.viewmodel.PokemonListEvent
import com.example.deepseekpokeapptwelve.features.pokemon.viewmodel.PokemonListViewModel
import com.example.deepseekpokeapptwelve.pokemon.domain.models.Pokemon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    navigator: IPokemonListNavigator,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(context) {
        viewModel.events.collect { event ->
            when (event) {
                is PokemonListEvent.ShowSuccess -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is PokemonListEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        topBar = {
            TopAppBar(
                title = { Text("Pokémon") }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading && state.pokemon.isEmpty() -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                state.error != null && state.pokemon.isEmpty() -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.error ?: "Unknown error",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadPokemon() }) {
                            Text("Retry")
                        }
                    }
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.pokemon) { pokemon ->
                            PokemonCard(
                                pokemon = pokemon,
                                onClick = { /* TODO: Navigate to detail */ }
                            )
                        }
                    }
                }
            }

            // Show loading indicator during refresh
            if (state.isRefreshing) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                )
            }
        }
    }
}

@Composable
fun PokemonCard(
    pokemon: Pokemon,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.8f),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Pokemon Image
            AsyncImage(
                model = pokemon.sprites.frontDefault,
                contentDescription = pokemon.name,
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        Color.Gray.copy(alpha = 0.1f),
                        RoundedCornerShape(8.dp)
                    ),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Pokemon Number
            Text(
                text = "#${pokemon.id.toString().padStart(3, '0')}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Pokemon Name
            Text(
                text = pokemon.name.replaceFirstChar { it.uppercaseChar() },
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Pokemon Types
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                pokemon.types.take(2).forEach { type ->
                    TypeChip(
                        typeName = type.name,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun TypeChip(
    typeName: String,
    modifier: Modifier = Modifier
) {
    val backgroundColor = getTypeColor(typeName)
    
    Surface(
        modifier = modifier.height(24.dp),
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor
    ) {
        Text(
            text = typeName.replaceFirstChar { it.uppercaseChar() },
            fontSize = 10.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(Alignment.CenterVertically)
        )
    }
}

@Composable
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
        else -> Color.Gray
    }
}
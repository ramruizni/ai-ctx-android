package com.ramruizni.deepseekpokeappfive.features.pokemon.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ramruizni.deepseekpokeappfive.features.pokemon.viewmodel.PokemonListViewModel
import com.ramruizni.deepseekpokeappfive.features.pokemon.viewmodel.PokemonListUiState

@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.refreshData()
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pokédex") }
            )
        }
    ) { innerPadding ->
        when (val state = uiState) {
            is PokemonListUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            
            is PokemonListUiState.Empty -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Pokémon found",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            
            is PokemonListUiState.Success -> {
                PokemonGrid(
                    pokemonList = state.pokemonList,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun PokemonGrid(
    pokemonList: List<com.ramruizni.deepseekpokeappfive.pokemon.domain.Pokemon>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pokemonList) { pokemon ->
            PokemonCard(pokemon = pokemon)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonCard(pokemon: com.ramruizni.deepseekpokeappfive.pokemon.domain.Pokemon) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.8f),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = { /* Handle click */ }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Pokemon Image
            if (pokemon.imageUrl.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(pokemon.imageUrl),
                    contentDescription = "${pokemon.name} image",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            
            // Pokemon Number
            Text(
                text = pokemon.displayNumber,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 8.dp)
            )
            
            // Pokemon Name
            Text(
                text = pokemon.name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
            
            // Pokemon Types
            if (pokemon.types.isNotEmpty()) {
                Text(
                    text = pokemon.types.joinToString(", ") { it.name.lowercase() },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
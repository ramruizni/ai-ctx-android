package com.ramruizni.deepseekpokeappeight.pokemon.view

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramruizni.deepseekpokeappeight.pokemon.domain.Pokemon
import com.ramruizni.deepseekpokeappeight.pokemon.domain.PokemonType
import com.ramruizni.deepseekpokeappeight.pokemon.viewmodel.PokemonListUiEvent
import com.ramruizni.deepseekpokeappeight.pokemon.viewmodel.PokemonListUiState
import com.ramruizni.deepseekpokeappeight.pokemon.viewmodel.PokemonListViewModel

/**
 * Main Pokemon list screen with lazy grid layout
 * Supports pull-to-refresh and proper state management
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = "Pokémon",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                ),
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        when (uiState) {
            is PokemonListUiState.Loading -> {
                LoadingIndicator(
                    message = "Loading Pokémon...",
                    modifier = Modifier.padding(paddingValues)
                )
            }
            
            is PokemonListUiState.Success -> {
                PokemonListContent(
                    pokemonList = uiState.pokemonList,
                    isRefreshing = uiState.isRefreshing,
                    onRefresh = { 
                        viewModel.onEvent(PokemonListUiEvent.Refresh)
                    },
                    onPokemonClick = { pokemon ->
                        viewModel.onEvent(PokemonListUiEvent.PokemonSelected(pokemon))
                    },
                    modifier = Modifier.padding(paddingValues)
                )
            }
            
            is PokemonListUiState.Error -> {
                ErrorMessage(
                    message = uiState.message,
                    canRetry = uiState.canRetry,
                    onRetry = if (uiState.canRetry) {
                        { viewModel.onEvent(PokemonListUiEvent.Retry) }
                    } else null,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

/**
 * Content for the success state showing the Pokemon grid
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PokemonListContent(
    pokemonList: List<Pokemon>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onPokemonClick: (Pokemon) -> Unit,
    modifier: Modifier = Modifier
) {
    val pullToRefreshState = rememberPullToRefreshState()
    
    if (pokemonList.isEmpty()) {
        // Empty state
        EmptyPokemonList(
            modifier = modifier.fillMaxSize()
        )
    } else {
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            state = pullToRefreshState,
            modifier = modifier.fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = pokemonList,
                    key = { pokemon -> pokemon.id }
                ) { pokemon ->
                    PokemonListItem(
                        pokemon = pokemon,
                        onPokemonClick = onPokemonClick
                    )
                }
            }
        }
    }
}

/**
 * Empty state when no Pokemon are available
 */
@Composable
private fun EmptyPokemonList(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                text = "No Pokémon Found",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Text(
                text = "Pull down to refresh and load Pokémon",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

/**
 * Loading state with shimmer placeholders
 */
@Composable
private fun PokemonListLoadingContent(
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(6) { // Show 6 placeholder items
            PokemonListItemPlaceholder()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PokemonListScreenSuccessPreview() {
    MaterialTheme {
        PokemonListContent(
            pokemonList = listOf(
                Pokemon(
                    id = 25,
                    name = "pikachu",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png",
                    types = listOf(PokemonType(name = "electric"))
                ),
                Pokemon(
                    id = 6,
                    name = "charizard",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png",
                    types = listOf(
                        PokemonType(name = "fire"),
                        PokemonType(name = "flying")
                    )
                ),
                Pokemon(
                    id = 9,
                    name = "blastoise",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/9.png",
                    types = listOf(PokemonType(name = "water"))
                )
            ),
            isRefreshing = false,
            onRefresh = { },
            onPokemonClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyPokemonListPreview() {
    MaterialTheme {
        EmptyPokemonList()
    }
}

@Preview(showBackground = true)
@Composable
private fun PokemonListLoadingContentPreview() {
    MaterialTheme {
        PokemonListLoadingContent()
    }
}
package com.ramruizni.deepseekpokeappthirteen.pokemon.datasource.local

import com.ramruizni.deepseekpokeappthirteen.pokemon.datasource.dbdtos.PokemonDbDto
import kotlinx.coroutines.flow.Flow

interface PokemonDataSource {
    fun getAllPokemon(): Flow<List<PokemonDbDto>>
    suspend fun getPokemonById(id: Int): PokemonDbDto?
    suspend fun insertPokemon(pokemon: PokemonDbDto)
    suspend fun insertAllPokemon(pokemon: List<PokemonDbDto>)
    suspend fun deleteAllPokemon()
}
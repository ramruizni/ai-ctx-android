package com.ramruizni.deepseekpokeappthirteen.pokemon.datasource.local

import com.ramruizni.deepseekpokeappthirteen.pokemon.datasource.daos.PokemonDao
import com.ramruizni.deepseekpokeappthirteen.pokemon.datasource.dbdtos.PokemonDbDto
import kotlinx.coroutines.flow.Flow

class PokemonDataSourceImpl(
    private val pokemonDao: PokemonDao
) : PokemonDataSource {
    
    override fun getAllPokemon(): Flow<List<PokemonDbDto>> {
        return pokemonDao.getAllPokemon()
    }
    
    override suspend fun getPokemonById(id: Int): PokemonDbDto? {
        return pokemonDao.getPokemonById(id)
    }
    
    override suspend fun insertPokemon(pokemon: PokemonDbDto) {
        pokemonDao.insertPokemon(pokemon)
    }
    
    override suspend fun insertAllPokemon(pokemon: List<PokemonDbDto>) {
        pokemonDao.insertAllPokemon(pokemon)
    }
    
    override suspend fun deleteAllPokemon() {
        pokemonDao.deleteAllPokemon()
    }
}
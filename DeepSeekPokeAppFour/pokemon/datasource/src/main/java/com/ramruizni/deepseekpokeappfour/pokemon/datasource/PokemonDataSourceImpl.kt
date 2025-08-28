package com.ramruizni.deepseekpokeappfour.pokemon.datasource

import kotlinx.coroutines.flow.Flow

class PokemonDataSourceImpl(
    private val pokemonDao: PokemonDao
) : PokemonDataSource {
    
    override fun getAllPokemon(): Flow<List<PokemonDbDto>> {
        return pokemonDao.getAllPokemon()
    }
    
    override suspend fun insertPokemon(pokemon: List<PokemonDbDto>) {
        pokemonDao.insertPokemon(pokemon)
    }
    
    override suspend fun insertSinglePokemon(pokemon: PokemonDbDto) {
        pokemonDao.insertSinglePokemon(pokemon)
    }
    
    override fun getPokemonById(id: Int): Flow<PokemonDbDto?> {
        return pokemonDao.getPokemonById(id)
    }
    
    override suspend fun updatePokemon(pokemon: PokemonDbDto) {
        pokemonDao.updatePokemon(pokemon)
    }
    
    override suspend fun deletePokemon(pokemon: PokemonDbDto) {
        pokemonDao.deletePokemon(pokemon)
    }
    
    override suspend fun deleteAllPokemon() {
        pokemonDao.deleteAllPokemon()
    }
    
    override suspend fun getPokemonCount(): Int {
        return pokemonDao.getPokemonCount()
    }
}
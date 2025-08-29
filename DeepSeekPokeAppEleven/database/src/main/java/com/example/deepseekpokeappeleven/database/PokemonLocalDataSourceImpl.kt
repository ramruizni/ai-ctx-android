package com.example.deepseekpokeappeleven.database

import com.example.deepseekpokeappeleven.database.daos.PokemonDao
import com.example.deepseekpokeappeleven.pokemon.datasource.entities.PokemonEntity
import com.example.deepseekpokeappeleven.pokemon.datasource.local.PokemonLocalDataSource
import kotlinx.coroutines.flow.Flow

class PokemonLocalDataSourceImpl(
    private val pokemonDao: PokemonDao
) : PokemonLocalDataSource {

    override fun getPokemonList(limit: Int, offset: Int): Flow<List<PokemonEntity>> {
        return pokemonDao.getPokemonList(limit, offset)
    }

    override suspend fun getPokemonById(id: Int): PokemonEntity? {
        return pokemonDao.getPokemonById(id)
    }

    override suspend fun insertPokemon(pokemon: List<PokemonEntity>) {
        pokemonDao.insertPokemon(pokemon)
    }

    override suspend fun clearAll() {
        pokemonDao.clearAll()
    }
}
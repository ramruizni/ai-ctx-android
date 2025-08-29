package com.ramruizni.deepseekpokeappfourteen.pokemon.datasource

import com.ramruizni.deepseekpokeappfourteen.database.daos.PokemonDao
import com.ramruizni.deepseekpokeappfourteen.database.entities.PokemonEntity
import kotlinx.coroutines.flow.Flow

class PokemonDataSourceImpl(
    private val pokemonDao: PokemonDao
) : PokemonDataSource {

    override fun getAllPokemon(): Flow<List<PokemonEntity>> {
        return pokemonDao.getAllPokemon()
    }

    override suspend fun getPokemonById(id: Int): PokemonEntity? {
        return pokemonDao.getPokemonById(id)
    }

    override suspend fun insertAll(pokemon: List<PokemonEntity>) {
        pokemonDao.insertAll(pokemon)
    }

    override suspend fun insert(pokemon: PokemonEntity) {
        pokemonDao.insert(pokemon)
    }

    override suspend fun deleteAll() {
        pokemonDao.deleteAll()
    }
}
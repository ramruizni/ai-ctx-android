package com.ramruizni.deepseekpokeappfive.pokemon.datasource.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PokemonListResponse
    
    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(@Path("name") name: String): PokemonDetailResponse
}

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonListItem>
)

data class PokemonListItem(
    val name: String,
    val url: String
)

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val sprites: PokemonSprites,
    val types: List<PokemonTypeSlot>,
    val abilities: List<PokemonAbilitySlot>,
    val stats: List<PokemonStatSlot>
)

data class PokemonSprites(
    val front_default: String?
)

data class PokemonTypeSlot(
    val slot: Int,
    val type: NamedApiResource
)

data class PokemonAbilitySlot(
    val ability: NamedApiResource,
    val is_hidden: Boolean,
    val slot: Int
)

data class PokemonStatSlot(
    val base_stat: Int,
    val effort: Int,
    val stat: NamedApiResource
)

data class NamedApiResource(
    val name: String,
    val url: String
)
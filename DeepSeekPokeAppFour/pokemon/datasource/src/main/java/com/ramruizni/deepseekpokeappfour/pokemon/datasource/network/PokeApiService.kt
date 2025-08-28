package com.ramruizni.deepseekpokeappfour.pokemon.datasource.network

/**
 * Retrofit service interface for PokeAPI integration
 * 
 * TODO: When implementing network layer:
 * 1. Add Retrofit dependencies to build.gradle.kts
 * 2. Add OkHttp for logging and networking
 * 3. Configure JSON serialization with Retrofit converter
 * 4. Create DI module for network dependencies
 * 
 * Example implementation:
 */

/*
@Serializable
interface PokeApiService {
    
    @GET("pokemon/")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PokemonListResponseDto
    
    @GET("pokemon/{id}")
    suspend fun getPokemonById(@Path("id") id: Int): PokemonApiDto
    
    @GET("pokemon/{name}")
    suspend fun getPokemonByName(@Path("name") name: String): PokemonApiDto
    
    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}
*/

/**
 * Network mapper functions for converting API DTOs to DbDtos
 * 
 * TODO: Implement these extension functions when adding network layer:
 */

/*
fun PokemonApiDto.toDbDto(): PokemonDbDto {
    return PokemonDbDto(
        id = this.id,
        name = this.name,
        number = this.order,
        imageUrl = this.sprites.other?.official_artwork?.front_default 
            ?: this.sprites.front_default ?: "",
        types = Json.encodeToString(this.types.map { it.type.name }),
        abilities = Json.encodeToString(this.abilities.map { it.ability.name }),
        statsHp = this.stats.find { it.stat.name == "hp" }?.base_stat ?: 0,
        statsAttack = this.stats.find { it.stat.name == "attack" }?.base_stat ?: 0,
        statsDefense = this.stats.find { it.stat.name == "defense" }?.base_stat ?: 0,
        statsSpecialAttack = this.stats.find { it.stat.name == "special-attack" }?.base_stat ?: 0,
        statsSpecialDefense = this.stats.find { it.stat.name == "special-defense" }?.base_stat ?: 0,
        statsSpeed = this.stats.find { it.stat.name == "speed" }?.base_stat ?: 0
    )
}
*/
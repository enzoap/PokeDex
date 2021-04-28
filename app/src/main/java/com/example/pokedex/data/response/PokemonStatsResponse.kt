package com.example.pokedex.data.response

import com.example.pokedex.data.model.Pokemon
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonStatsResponse(
        @Json(name = "base_stat")
        val baseStat: Int,

        @Json(name = "stat")
        val statResponse: PokemonStatNameResponse
){
    val pokemonModel: Pokemon
        get() = Pokemon(
                baseStat,
                statResponse.statName,
                "",
                ""
        )
}

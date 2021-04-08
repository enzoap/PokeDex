package com.example.pokedex.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonBodyResponse(
    @Json(name = "results")
    val pokemonResult: List<PokemonResultResponse>
)

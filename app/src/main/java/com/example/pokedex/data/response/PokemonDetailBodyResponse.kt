package com.example.pokedex.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDetailBodyResponse(
        @Json(name = "stats")
        val statsResult : List<PokemonStatsResponse>,
        @Json(name = "types")
        val typeResult: List<PokemonTypesResponse>

)

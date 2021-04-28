package com.example.pokedex.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonTypesResponse(
        @Json(name = "type")
        val type: PokemonTypeResponse
)
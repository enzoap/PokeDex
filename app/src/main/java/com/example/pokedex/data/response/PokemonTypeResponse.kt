package com.example.pokedex.data.response

import com.example.pokedex.data.model.Pokemon
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonTypeResponse(
        @Json(name = "name")
        val name: String
)

package com.example.pokedex.data.response

import com.example.pokedex.data.model.PokemonList
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonResultResponse(
    @Json(name = "name")
    val name: String,

    @Json(name = "url")
    val urlDetail: String
) {
    fun getPokemonModel() = PokemonList(
        name = this.name,
        urlDetail = this.urlDetail
    )
}
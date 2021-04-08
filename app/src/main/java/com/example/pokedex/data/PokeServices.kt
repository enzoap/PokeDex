package com.example.pokedex.data

import com.example.pokedex.data.response.PokemonBodyResponse
import retrofit2.Call
import retrofit2.http.GET

interface PokeServices {
    @GET("pokemon")
    fun getPokemons(): Call<PokemonBodyResponse>
}
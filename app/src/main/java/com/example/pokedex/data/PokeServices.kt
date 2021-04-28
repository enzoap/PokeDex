package com.example.pokedex.data

import com.example.pokedex.data.response.PokemonBodyResponse
import com.example.pokedex.data.response.PokemonDetailBodyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeServices {
    @GET("pokemon")
    fun getPokemons(): Call<PokemonBodyResponse>

    @GET("pokemon/{id}")
    fun getPokemon(@Path("id") id: Int): Call<PokemonDetailBodyResponse>
}
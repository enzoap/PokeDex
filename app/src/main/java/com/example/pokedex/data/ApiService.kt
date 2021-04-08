package com.example.pokedex.data

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiService {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    private fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
    }

    val service: PokeServices = initRetrofit().create(PokeServices::class.java)
}
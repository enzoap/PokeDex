package com.example.pokedex.data.repository

import com.example.pokedex.data.ApiService
import com.example.pokedex.data.PokemonResult
import com.example.pokedex.data.model.Pokemon
import com.example.pokedex.data.response.PokemonDetailBodyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonDetailApiDataSource: PokemonDetailRepository {
    override fun getPokemon(id: Int, pokemonResultCallback: (pokemonResult: PokemonResult) -> Unit) {
        ApiService.service.getPokemon(id).enqueue(object : Callback<PokemonDetailBodyResponse> {
            override fun onResponse(call: Call<PokemonDetailBodyResponse>, response: Response<PokemonDetailBodyResponse>) {
                when {
                    response.isSuccessful -> {
                        val statsResponse = mutableListOf<Pokemon>()

                        response.body()?.let { pokemonDetailBodyResponse ->
                            for (result in pokemonDetailBodyResponse.statsResult){
                                val pokemon = result.pokemonModel
                                pokemon.type1 = pokemonDetailBodyResponse.typeResult[0].type.name
                                if(pokemonDetailBodyResponse.typeResult.size > 1){
                                    pokemon.type2 = pokemonDetailBodyResponse.typeResult[1].type.name
                                }else {
                                    pokemon.type2 = ""
                                }
                                statsResponse.add(pokemon)
                            }
                        }
                        pokemonResultCallback(PokemonResult.Success(statsResponse))
                    }
                    else -> pokemonResultCallback(PokemonResult.ApiError(response.code()))
                }
            }

            override fun onFailure(call: Call<PokemonDetailBodyResponse>, t: Throwable) {
                pokemonResultCallback(PokemonResult.ServerError)
            }
        })
    }
}
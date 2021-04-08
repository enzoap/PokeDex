package com.example.pokedex.data.repository

import com.example.pokedex.data.ApiService
import com.example.pokedex.data.PokemonsListResult
import com.example.pokedex.data.model.PokemonList
import com.example.pokedex.data.response.PokemonBodyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonsApiDataSource: PokemonsRepository {
    override fun getPokemons(pokemonsResultCallback: (listResult: PokemonsListResult) -> Unit) {
        ApiService.service.getPokemons().enqueue(object : Callback<PokemonBodyResponse> {
            override fun onResponse(
                call: Call<PokemonBodyResponse>,
                response: Response<PokemonBodyResponse>
            ) {
                when {
                    response.isSuccessful -> {
                        val pokemonList: MutableList<PokemonList> = mutableListOf()

                        response.body()?.let {
                            for (result in it.pokemonResult){
                                val pokemon = result.getPokemonModel()
                                pokemonList.add(pokemon)

                            }
                        }
                        pokemonsResultCallback(PokemonsListResult.Success(pokemonList))
                    }
                    else -> pokemonsResultCallback(PokemonsListResult.ApiError(response.code()))
                }
            }

            override fun onFailure(call: Call<PokemonBodyResponse>, t: Throwable) {
                pokemonsResultCallback(PokemonsListResult.ServerError)
            }
        })
    }
}